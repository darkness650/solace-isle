package com.solaceisle.constant;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 提醒文案常量：每一条提醒一对 KEY / VALUE。
 * KEY: 文案描述（用于标识、i18n 或存储）
 * VALUE: 实际提示内容
 */
public class RemindConstant {
    // 1. 心灵树洞
    public static final String HOLE_CHECK_IN_KEY = "心灵树洞打卡提醒";
    public static final String HOLE_CHECK_IN_VALUE = "记得打卡心灵树洞哦，去看看有哪些新评论吧";

    // 2. 日记
    public static final String DIARY_CHECK_IN_KEY = "日记打卡提醒";
    public static final String DIARY_CHECK_IN_VALUE = "记得打卡日记哦，记录美好生活";

    // 3. CBT 测试
    public static final String CBT_TEST_KEY = "CBT测试提醒";
    public static final String CBT_TEST_VALUE = "记得去看看cbt测试哦，相信你是最棒的";

    // 4. 早安
    public static final String MORNING_GREETING_KEY = "早安问候";
    public static final String MORNING_GREETING_VALUE = "早上好，今天心情怎么样";

    // 5. 早餐
    public static final String BREAKFAST_REMIND_KEY = "早餐提醒";
    public static final String BREAKFAST_REMIND_VALUE = "该吃早饭了哦，吃好饭身体好";

    // 6. 午安
    public static final String NOON_GREETING_KEY = "午安问候";
    public static final String NOON_GREETING_VALUE = "中午好，今天心情怎么样";

    // 7. 午餐
    public static final String LUNCH_REMIND_KEY = "午餐提醒";
    public static final String LUNCH_REMIND_VALUE = "该吃午饭了哦，吃好饭身体好";

    // 8. 晚上/傍晚问候
    public static final String EVENING_GREETING_KEY = "晚间问候";
    public static final String EVENING_GREETING_VALUE = "晚上好，今天心情怎么样";

    // 9. 晚餐
    public static final String DINNER_REMIND_KEY = "晚餐提醒";
    public static final String DINNER_REMIND_VALUE = "该吃晚饭了哦，吃好饭身体好";

    // 10. 深夜
    public static final String LATE_NIGHT_KEY = "深夜休息提醒";
    public static final String LATE_NIGHT_VALUE = "深夜了，早点睡觉吧，有什么不开心的可以去和ai助手聊聊";

    public static final String GREETINGS="greetings";
    // ================== 聚合常量 ==================
    /**
     * 前三条提醒的只读 Map，保持插入顺序。
     */
    public static final Map<String, String> FIRST_THREE_REMINDERS;
    static {
        Map<String, String> temp = new LinkedHashMap<>();
        temp.put(HOLE_CHECK_IN_KEY, HOLE_CHECK_IN_VALUE);
        temp.put(DIARY_CHECK_IN_KEY, DIARY_CHECK_IN_VALUE);
        temp.put(CBT_TEST_KEY, CBT_TEST_VALUE);
        FIRST_THREE_REMINDERS = Collections.unmodifiableMap(temp);
    }
}
