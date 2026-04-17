package com.xiaou.resource;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaou.resource.entity.Answer;
import com.xiaou.resource.entity.Note;
import com.xiaou.resource.entity.PointsRecord;
import com.xiaou.resource.entity.QuestionAnswer;
import com.xiaou.resource.entity.Resource;
import com.xiaou.resource.entity.User;
import com.xiaou.resource.service.AnswerService;
import com.xiaou.resource.service.NoteService;
import com.xiaou.resource.service.PointsRecordService;
import com.xiaou.resource.service.QuestionAnswerService;
import com.xiaou.resource.service.ResourceRatingService;
import com.xiaou.resource.service.ResourceService;
import com.xiaou.resource.service.UserService;
import com.xiaou.resource.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceBehaviorTest {

    private UserService userService;

    private ResourceService resourceService;

    private AnswerService answerService;

    private NoteService noteService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserService rewardUserService;

    @Mock
    private ResourceRatingService resourceRatingService;

    @Mock
    private QuestionAnswerService questionAnswerService;

    @Mock
    private PointsRecordService pointsRecordService;

    @BeforeEach
    void setUp() {
        userService = spy(new UserService());
        resourceService = spy(new ResourceService());
        answerService = spy(new AnswerService());
        noteService = spy(new NoteService());

        ReflectionTestUtils.setField(userService, "jwtUtil", jwtUtil);
        ReflectionTestUtils.setField(resourceService, "userService", rewardUserService);
        ReflectionTestUtils.setField(resourceService, "resourceRatingService", resourceRatingService);
        ReflectionTestUtils.setField(resourceService, "pointsRecordService", pointsRecordService);
        ReflectionTestUtils.setField(answerService, "questionAnswerService", questionAnswerService);
        ReflectionTestUtils.setField(answerService, "userService", rewardUserService);
        ReflectionTestUtils.setField(answerService, "pointsRecordService", pointsRecordService);
    }

    @Test
    void loginShouldHidePasswordInResponse() {
        User dbUser = new User();
        dbUser.setId(2L);
        dbUser.setUsername("student1");
        dbUser.setPassword(DigestUtil.md5Hex("123456"));
        dbUser.setPoints(500);

        doReturn(dbUser).when(userService).getOne(any(QueryWrapper.class));
        when(jwtUtil.generateToken(2L, "student1")).thenReturn("token-020");

        Object responseUser = userService.login("student1", "123456").get("user");

        Assertions.assertInstanceOf(User.class, responseUser);
        User safeUser = (User) responseUser;
        Assertions.assertNull(safeUser.getPassword());
        Assertions.assertEquals("student1", safeUser.getUsername());
        Assertions.assertEquals(DigestUtil.md5Hex("123456"), dbUser.getPassword());
    }

    @Test
    void uploadResourceShouldAwardPointsAndRecordLog() {
        Resource resource = new Resource();
        resource.setTitle("离散数学整理");
        resource.setPoints(8);

        doReturn(true).when(resourceService).save(any(Resource.class));
        when(rewardUserService.addPoints(3L, 10)).thenReturn(true);
        when(pointsRecordService.save(any(PointsRecord.class))).thenReturn(true);

        boolean success = resourceService.uploadResource(resource, 3L);

        Assertions.assertTrue(success);
        verify(rewardUserService).addPoints(3L, 10);
        verify(pointsRecordService).save(any(PointsRecord.class));
        Assertions.assertEquals(3L, resource.getUserId());
    }

    @Test
    void addAnswerShouldRejectSelfAnswer() {
        QuestionAnswer question = new QuestionAnswer();
        question.setId(9L);
        question.setAskerId(6L);
        question.setStatus("pending");

        Answer answer = new Answer();
        answer.setQuestionAnswerId(9L);
        answer.setContent("自己来回答");

        when(questionAnswerService.getById(9L)).thenReturn(question);

        boolean success = answerService.addAnswer(answer, 6L);

        Assertions.assertFalse(success);
        verify(rewardUserService, never()).addPoints(eq(6L), any(Integer.class));
    }

    @Test
    void addAnswerShouldAwardPointsForValidAnswer() {
        QuestionAnswer question = new QuestionAnswer();
        question.setId(11L);
        question.setTitle("Spring Boot 事务传播");
        question.setAskerId(2L);
        question.setStatus("pending");

        Answer answer = new Answer();
        answer.setQuestionAnswerId(11L);
        answer.setContent("可以从 REQUIRED 开始理解");

        when(questionAnswerService.getById(11L)).thenReturn(question);
        doReturn(0L).when(answerService).count(any(QueryWrapper.class));
        doReturn(true).when(answerService).save(any(Answer.class));
        when(rewardUserService.addPoints(5L, 10)).thenReturn(true);
        when(pointsRecordService.save(any(PointsRecord.class))).thenReturn(true);

        boolean success = answerService.addAnswer(answer, 5L);

        Assertions.assertTrue(success);
        verify(rewardUserService).addPoints(5L, 10);
        verify(pointsRecordService).save(any(PointsRecord.class));
        Assertions.assertEquals(5L, answer.getUserId());
    }

    @Test
    void privateNoteShouldNotBeVisibleToOtherUsers() {
        Note note = new Note();
        note.setId(7L);
        note.setUserId(2L);
        note.setIsPublic(0);
        note.setViewCount(3);

        doReturn(note).when(noteService).getById(7L);

        Note result = noteService.getNoteDetail(7L, 5L);

        Assertions.assertNull(result);
        verify(noteService, never()).updateById(any(Note.class));
    }
}
