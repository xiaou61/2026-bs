package com.xiaou.ticket.config;

import cn.hutool.crypto.digest.BCrypt;
import org.jooq.DSLContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DSLContext dsl;

    public DataInitializer(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public void run(String... args) {
        Integer userCount = dsl.fetchCount(table("user"));
        if (userCount != null && userCount > 0) {
            return;
        }

        seedUsers();
        Map<String, Long> stadiumIds = seedStadiums();
        Map<String, Long> teamIds = seedTeams();
        Map<String, CategorySeed> categorySeeds = seedCategories(stadiumIds);
        Map<String, Long> matchIds = seedMatches(teamIds, stadiumIds);
        seedPricing(matchIds, categorySeeds);
    }

    private void seedUsers() {
        insertUser("admin", "admin123", "13800138000", "admin@ticket.com", "系统管理员", "110101199001011234", "ADMIN", new BigDecimal("10000.00"));
        insertUser("user1", "user123", "13800138001", "user1@test.com", "张三", "110101199001011235", "USER", new BigDecimal("5000.00"));
        insertUser("user2", "user123", "13800138002", "user2@test.com", "李四", "110101199001011236", "USER", new BigDecimal("3000.00"));
    }

    private Map<String, Long> seedStadiums() {
        Map<String, Long> stadiumIds = new LinkedHashMap<>();
        stadiumIds.put("北京工人体育场", insertStadium("北京工人体育场", "北京市朝阳区工人体育场北路", "北京", 66000, "中国著名的综合性体育场", "停车场,餐饮区,VIP包厢"));
        stadiumIds.put("上海虹口足球场", insertStadium("上海虹口足球场", "上海市虹口区东江湾路444号", "上海", 35000, "中国第一座专业足球场", "地铁直达,商业中心"));
        stadiumIds.put("广州天河体育中心", insertStadium("广州天河体育中心", "广州市天河区天河路299号", "广州", 60000, "华南地区大型体育场馆", "停车场,商场,地铁站"));
        stadiumIds.put("深圳大运中心", insertStadium("深圳大运中心", "深圳市龙岗区龙翔大道", "深圳", 40000, "大型综合体育中心", "地铁直达,购物中心"));
        return stadiumIds;
    }

    private Map<String, Long> seedTeams() {
        Map<String, Long> teamIds = new LinkedHashMap<>();
        teamIds.put("北京国安", insertTeam("北京国安", "/images/teams/beijing.png", "中国", "北京", 1992, "中超传统豪门"));
        teamIds.put("上海申花", insertTeam("上海申花", "/images/teams/shanghai.png", "中国", "上海", 1993, "中国足坛老牌劲旅"));
        teamIds.put("广州队", insertTeam("广州队", "/images/teams/guangzhou.png", "中国", "广州", 1993, "南方传统球队"));
        teamIds.put("山东泰山", insertTeam("山东泰山", "/images/teams/shandong.png", "中国", "济南", 1993, "中超传统强队"));
        teamIds.put("上海海港", insertTeam("上海海港", "/images/teams/shanghai-port.png", "中国", "上海", 2005, "中超冠军球队"));
        teamIds.put("成都蓉城", insertTeam("成都蓉城", "/images/teams/chengdu.png", "中国", "成都", 2018, "中超新锐力量"));
        teamIds.put("深圳队", insertTeam("深圳队", "/images/teams/shenzhen.png", "中国", "深圳", 1994, "深圳足球代表队"));
        teamIds.put("武汉三镇", insertTeam("武汉三镇", "/images/teams/wuhan.png", "中国", "武汉", 2020, "2022 赛季中超冠军"));
        return teamIds;
    }

    private Map<String, CategorySeed> seedCategories(Map<String, Long> stadiumIds) {
        Map<String, CategorySeed> categorySeeds = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : stadiumIds.entrySet()) {
            String prefix = entry.getKey();
            Long stadiumId = entry.getValue();
            categorySeeds.put(prefix + "-VIP", insertCategory(stadiumId, "VIP座位", "最佳观赛区域", 4, 4));
            categorySeeds.put(prefix + "-A", insertCategory(stadiumId, "A区看台", "主队球迷看台", 4, 6));
            categorySeeds.put(prefix + "-B", insertCategory(stadiumId, "B区看台", "客队球迷看台", 4, 6));
        }
        return categorySeeds;
    }

    private Map<String, Long> seedMatches(Map<String, Long> teamIds, Map<String, Long> stadiumIds) {
        Map<String, Long> matchIds = new LinkedHashMap<>();
        matchIds.put("京沪大战", insertMatch("北京国安 VS 上海申花", teamIds.get("北京国安"), teamIds.get("上海申花"), stadiumIds.get("北京工人体育场"), nextMatchDate(7, 19, 35), "联赛", "中超", "2026", "强强对话，京沪大战"));
        matchIds.put("南北对决", insertMatch("广州队 VS 山东泰山", teamIds.get("广州队"), teamIds.get("山东泰山"), stadiumIds.get("广州天河体育中心"), nextMatchDate(10, 15, 30), "联赛", "中超", "2026", "南北对决"));
        matchIds.put("海港蓉城", insertMatch("上海海港 VS 成都蓉城", teamIds.get("上海海港"), teamIds.get("成都蓉城"), stadiumIds.get("上海虹口足球场"), nextMatchDate(14, 19, 35), "联赛", "中超", "2026", "争夺积分榜前列"));
        matchIds.put("深圳武汉", insertMatch("深圳队 VS 武汉三镇", teamIds.get("深圳队"), teamIds.get("武汉三镇"), stadiumIds.get("深圳大运中心"), nextMatchDate(18, 19, 35), "联赛", "中超", "2026", "卫冕冠军客场之旅"));
        return matchIds;
    }

    private void seedPricing(Map<String, Long> matchIds, Map<String, CategorySeed> categorySeeds) {
        insertPricing(matchIds.get("京沪大战"), categorySeeds.get("北京工人体育场-VIP"), new BigDecimal("688.00"));
        insertPricing(matchIds.get("京沪大战"), categorySeeds.get("北京工人体育场-A"), new BigDecimal("280.00"));
        insertPricing(matchIds.get("京沪大战"), categorySeeds.get("北京工人体育场-B"), new BigDecimal("180.00"));

        insertPricing(matchIds.get("南北对决"), categorySeeds.get("广州天河体育中心-VIP"), new BigDecimal("588.00"));
        insertPricing(matchIds.get("南北对决"), categorySeeds.get("广州天河体育中心-A"), new BigDecimal("260.00"));
        insertPricing(matchIds.get("南北对决"), categorySeeds.get("广州天河体育中心-B"), new BigDecimal("168.00"));

        insertPricing(matchIds.get("海港蓉城"), categorySeeds.get("上海虹口足球场-VIP"), new BigDecimal("688.00"));
        insertPricing(matchIds.get("海港蓉城"), categorySeeds.get("上海虹口足球场-A"), new BigDecimal("288.00"));
        insertPricing(matchIds.get("海港蓉城"), categorySeeds.get("上海虹口足球场-B"), new BigDecimal("188.00"));

        insertPricing(matchIds.get("深圳武汉"), categorySeeds.get("深圳大运中心-VIP"), new BigDecimal("528.00"));
        insertPricing(matchIds.get("深圳武汉"), categorySeeds.get("深圳大运中心-A"), new BigDecimal("238.00"));
        insertPricing(matchIds.get("深圳武汉"), categorySeeds.get("深圳大运中心-B"), new BigDecimal("158.00"));
    }

    private Long insertUser(String username, String rawPassword, String phone, String email, String realName, String idCard, String role, BigDecimal balance) {
        return dsl.insertInto(table("user"))
                .set(field("username"), username)
                .set(field("password"), BCrypt.hashpw(rawPassword))
                .set(field("phone"), phone)
                .set(field("email"), email)
                .set(field("real_name"), realName)
                .set(field("id_card"), idCard)
                .set(field("role"), role)
                .set(field("balance"), balance)
                .set(field("status"), "ACTIVE")
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();
    }

    private Long insertStadium(String name, String location, String city, int capacity, String description, String facilities) {
        return dsl.insertInto(table("stadium"))
                .set(field("name"), name)
                .set(field("location"), location)
                .set(field("city"), city)
                .set(field("capacity"), capacity)
                .set(field("description"), description)
                .set(field("facilities"), facilities)
                .set(field("status"), "ACTIVE")
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();
    }

    private Long insertTeam(String name, String logoUrl, String country, String city, int foundedYear, String description) {
        return dsl.insertInto(table("team"))
                .set(field("name"), name)
                .set(field("logo_url"), logoUrl)
                .set(field("country"), country)
                .set(field("city"), city)
                .set(field("founded_year"), foundedYear)
                .set(field("description"), description)
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();
    }

    private CategorySeed insertCategory(Long stadiumId, String name, String description, int rowCount, int columnCount) {
        int totalSeats = rowCount * columnCount;
        Long categoryId = dsl.insertInto(table("seat_category"))
                .set(field("stadium_id"), stadiumId)
                .set(field("name"), name)
                .set(field("description"), description)
                .set(field("total_seats"), totalSeats)
                .set(field("row_count"), rowCount)
                .set(field("column_count"), columnCount)
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();

        for (int row = 0; row < rowCount; row++) {
            String rowLabel = String.valueOf((char) ('A' + row));
            for (int column = 1; column <= columnCount; column++) {
                dsl.insertInto(table("seat"))
                        .set(field("category_id"), categoryId)
                        .set(field("row_number"), rowLabel)
                        .set(field("seat_number"), String.valueOf(column))
                        .set(field("status"), "AVAILABLE")
                        .execute();
            }
        }
        return new CategorySeed(categoryId, totalSeats);
    }

    private Long insertMatch(String title, Long homeTeamId, Long awayTeamId, Long stadiumId, LocalDateTime matchDate,
                             String matchType, String league, String season, String description) {
        return dsl.insertInto(table("match"))
                .set(field("title"), title)
                .set(field("home_team_id"), homeTeamId)
                .set(field("away_team_id"), awayTeamId)
                .set(field("stadium_id"), stadiumId)
                .set(field("match_date"), matchDate)
                .set(field("match_type"), matchType)
                .set(field("league"), league)
                .set(field("season"), season)
                .set(field("description"), description)
                .set(field("status"), "UPCOMING")
                .returningResult(field("id", Long.class))
                .fetchOne()
                .value1();
    }

    private void insertPricing(Long matchId, CategorySeed categorySeed, BigDecimal price) {
        dsl.insertInto(table("match_pricing"))
                .set(field("match_id"), matchId)
                .set(field("category_id"), categorySeed.categoryId())
                .set(field("price"), price)
                .set(field("available_seats"), categorySeed.totalSeats())
                .execute();
    }

    private LocalDateTime nextMatchDate(int plusDays, int hour, int minute) {
        return LocalDateTime.now()
                .withSecond(0)
                .withNano(0)
                .plusDays(plusDays)
                .withHour(hour)
                .withMinute(minute);
    }

    private record CategorySeed(Long categoryId, int totalSeats) {
    }
}
