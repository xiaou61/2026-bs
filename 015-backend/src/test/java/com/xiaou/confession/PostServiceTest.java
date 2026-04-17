package com.xiaou.confession;

import com.xiaou.confession.entity.Post;
import com.xiaou.confession.entity.User;
import com.xiaou.confession.mapper.PostMapper;
import com.xiaou.confession.mapper.SensitiveWordMapper;
import com.xiaou.confession.mapper.UserMapper;
import com.xiaou.confession.service.PostService;
import com.xiaou.confession.util.SensitiveWordFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostMapper postMapper;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SensitiveWordMapper sensitiveWordMapper;

    @Mock
    private SensitiveWordFilter sensitiveWordFilter;

    @Test
    void shouldMarkPostPendingWhenHighLevelSensitiveWordExists() {
        PostService postService = new PostService(postMapper, userMapper, sensitiveWordMapper, sensitiveWordFilter);

        User user = new User();
        user.setId(1L);
        user.setAuthStatus(2);
        user.setPoints(10);
        user.setPostCount(0);

        when(userMapper.selectById(1L)).thenReturn(user);
        when(sensitiveWordFilter.getSensitiveWords("包含政治内容")).thenReturn(List.of("政治"));
        when(sensitiveWordFilter.filterSensitiveWord("包含政治内容", "*")).thenReturn("包含**内容");
        when(sensitiveWordMapper.selectCount(any())).thenReturn(1L);
        doAnswer(invocation -> {
            Post post = invocation.getArgument(0);
            post.setId(100L);
            return 1;
        }).when(postMapper).insert(any(Post.class));

        Post result = postService.createPost(1L, "CONFESS", "测试标题", "包含政治内容", null, null);

        assertEquals(0, result.getStatus());
        assertEquals("政治", result.getSensitiveWords());
        assertTrue(result.getPostNo().startsWith("#"));

        ArgumentCaptor<Post> captor = ArgumentCaptor.forClass(Post.class);
        verify(postMapper).updateById(captor.capture());
        assertEquals("#00100", captor.getValue().getPostNo());
    }
}
