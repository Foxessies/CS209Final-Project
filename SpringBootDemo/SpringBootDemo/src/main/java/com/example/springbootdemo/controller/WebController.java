package com.example.springbootdemo.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootdemo.model.Developer;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class WebController {

  String owner = null;
  String repo = null;

//    @GetMapping("/repo")
//    public void postRepo(@RequestParam("OWNER") String owner, @RequestParam("REPO") String repo,
//    @PathVariable String OWNER, @PathVariable String REPO){
//        this.owner = owner;
//        this.repo = repo;
//        System.out.println(repo);
    //    }
    @GetMapping("/committers")
    public ArrayList<Object> getCommitters() {
        String s = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main\\java" +
                "\\com\\example\\springbootdemo\\repository\\socketio\\commits_data2.txt");
        List<JSONObject> objects = JSONArray.parseArray(s, JSONObject.class);
        System.out.println(objects.size());
        ArrayList<JSONObject> commits = new ArrayList<>(objects);

        HashMap<String, Integer> committer_Map = new HashMap<>();
        for (JSONObject commit : commits) {
            String name = commit.getJSONObject("commit").getJSONObject("author").get("name").toString();
            if (committer_Map.containsKey(name)) {
                committer_Map.put(name, committer_Map.get(name) + 1);
            } else {
                committer_Map.put(name, 1);
            }
        }
        List<Map.Entry<String, Integer>> infoIds = new ArrayList<>(committer_Map.entrySet());
        infoIds.sort((o1, o2) -> {
            return (o2.getValue() - o1.getValue());
            //return (o1.getKey()).toString().compareTo(o2.getKey());
        });
        ArrayList<Developer> developers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (JSONObject commit: commits) {
                if (infoIds.get(i).getKey().equals(commit.getJSONObject("commit").getJSONObject("author").get("name").toString())) {
                    String name = commit.getJSONObject("commit").getJSONObject("author").get("name").toString();
                    String email = commit.getJSONObject("commit").getJSONObject("author").get("email").toString();
                    if (commit.getJSONObject("author") != null) {
                        String repos_url = commit.getJSONObject("author").get("repos_url").toString();
                        String id = commit.getJSONObject("author").get("id").toString();
                        String login = commit.getJSONObject("author").get("login").toString();
                        Developer developer = new Developer(name, email, repos_url, id, "https://github.com/" + login + ".png");
                        developers.add(developer);
                        break;
                    }

                }
            }
        }
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(infoIds.size());
        arrayList.add(developers);
        return arrayList;

    }


    public String readFile(String fileName) {
//        StringBuilder sb = new StringBuilder();
//        File file = new File(fileName);
//        try {
//            byte[] bytes = new byte[1024];
//            FileInputStream in = new FileInputStream(file);
//            int len;
//            while ((len = in.read(bytes)) != -1) {
//                sb.append(new String(bytes, 0, len));
//            }
//
//            return sb.toString();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
        //        }

        StringBuilder strSb = new StringBuilder();
        InputStreamReader inStrR;
        try {
            inStrR = new InputStreamReader(Files.newInputStream(Paths.get(fileName)));
            BufferedReader br = new BufferedReader(inStrR);
            String line;
            line = br.readLine();
            while (line != null) {
                if (!line.equals("][")) {
                    strSb.append(line).append("\r\n");

                }
                line = br.readLine();
            }
            return strSb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/issues")
    public ArrayList<Object> getIssues() {
        String s = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main\\java\\com\\example\\springbootdemo\\repository\\socketio\\open_issues.txt");
        List<JSONObject> open_issues = JSONArray.parseArray(s, JSONObject.class);
        int open_number = open_issues.size();

        String s1 = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main" +
                "\\java\\com\\example\\springbootdemo\\repository\\socketio\\closed_issues.txt");
        List<JSONObject> closed_issues = JSONArray.parseArray(s1, JSONObject.class);
        int closed_number = closed_issues.size();

        HashMap<Integer, Integer> resolutionTime_Map = new HashMap<>();
        ArrayList<Long> minuteList = new ArrayList<>();
        for (JSONObject closed_issue : closed_issues) {
            String created_time = closed_issue.get("created_at").toString().replaceAll("[a-zA-Z]", " ");
            String closed_time = closed_issue.get("closed_at").toString().replaceAll("[a-zA-Z]", " ");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date created_date = simpleDateFormat.parse(created_time);
                Date closed_date = simpleDateFormat.parse(closed_time);
                int differentDays = differentDays(created_date, closed_date);
                long difference = closed_date.getTime() - created_date.getTime();
                long minute_difference = difference / (1000 * 60 * 60 * 24);
                minuteList.add(minute_difference);
                if (resolutionTime_Map.containsKey(differentDays)) {
                    resolutionTime_Map.put(differentDays, resolutionTime_Map.get(differentDays) + 1);
                } else {
                    resolutionTime_Map.put(differentDays, 1);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        List<Map.Entry<Integer, Integer>> infoIds = new ArrayList<>(resolutionTime_Map.entrySet());
        infoIds.sort((o1, o2) -> {
            return (o2.getValue() - o1.getValue());
            //return (o1.getKey()).toString().compareTo(o2.getKey());
        });

        int count = 0; //总个数
        double sum = 0; //总和
        double average; //平均数
        double Dev; //总体方差
        count = minuteList.size();
        for (Long aLong : minuteList) {
            sum += aLong;
        }
        //求平均数
        average = sum / minuteList.size();
        DecimalFormat df = new DecimalFormat(".000");
        double dsum = 0;
        for (Long aLong : minuteList) {
            double ss = aLong - average;
            dsum += Math.pow(ss, 2);
        }
        Dev = dsum / (minuteList.size() - 1);

        Long max = Collections.max(minuteList);
        Long min = Collections.min(minuteList);
        Long range = max - min;

        HashMap<String, Object> typical = new HashMap<>();
        typical.put("极差", range);
        typical.put("平均值", average);
        typical.put("方差", Dev);

        ArrayList<String> topic_list = new ArrayList<>();
        for (JSONObject open_issue : open_issues) {
            String title = open_issue.get("title").toString();
            topic_list.add(title);
            String labels = open_issue.getJSONArray("labels").toString();
            List<JSONObject> labelObject = JSONArray.parseArray(labels, JSONObject.class);
            for (JSONObject label : labelObject) {
                String description = label.get("description").toString();
                topic_list.add(description);
            }
        }
        String s2 = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main\\java\\com\\example\\springbootdemo\\repository\\socketio\\issues_comments.txt");
        List<JSONObject> issues_comments = JSONArray.parseArray(s2, JSONObject.class);
        for (JSONObject issues_comment : issues_comments) {
            String comment = issues_comment.get("body").toString();
            topic_list.add(comment);
        }

        HashMap<String, Integer> topicMap = new HashMap<>();
        String[] notImportantWords = new String[]{"", "the", "to", "I", "a", "it", "this", "is", 
                "and", "on", "in", "for", "of", "that", "you", "with", "at", "be", "not", "s", "have", 
                "but", "as", "or", "if", "can", "are", "from", "using", "m", "just", "was", "will", "so", "my", 
                "1", "0", "2", "3", "4", "5", "6", "7", "8", "9"};
        System.out.println(notImportantWords.length);
        for (String topic : topic_list) {
            String[] split = topic.replaceAll("\\W", " ").split(" ");
            for (String splitString : split) {
                boolean notImportant = false;
                for (String notImportantWord : notImportantWords) {
                    if (splitString.equals(notImportantWord)) {
                        notImportant = true;
                        break;
                    }
                }
                if (!notImportant) {
                    if (topicMap.containsKey(splitString)) {
                        topicMap.put(splitString, topicMap.get(splitString) + 1);
                    } else {
                        topicMap.put(splitString, 1);
                    }
                }


            }

        }
        List<Map.Entry<String, Integer>> topicEntry = new ArrayList<>(topicMap.entrySet());
        topicEntry.sort((o1, o2) -> {
            return (o2.getValue() - o1.getValue());
            //return (o1.getKey()).toString().compareTo(o2.getKey());
        });
        System.out.println(topicEntry);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : topicEntry) {
            names.add(entry.getKey());
            values.add(entry.getValue());
        }

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(open_number);
        arrayList.add(closed_number);
//        arrayList.add(infoIds);
//        arrayList.add(typical);
        arrayList.add(range);
        arrayList.add(average);
        arrayList.add(Dev);
        arrayList.add(names);
        arrayList.add(values);
        return arrayList;
    }

    private static int differentDays(Date date1,Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)  {//同一年
            int timeDistance = 0 ;
            for (int i = year1; i < year2; i++)
            {
                if (i % 4 == 0 && i % 100 !=0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1) ;
        } else  {// 不同年
            return day2 - day1;
        }
    }

    @GetMapping("/releases")
    public ArrayList<Object> getRelease() {
        //获取release次数
        String releaseString = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main\\java\\com\\example\\springbootdemo\\repository\\socketio\\releases.txt");
        List<JSONObject> releases = JSONArray.parseArray(releaseString, JSONObject.class);

        //获取每次release之间的commit个数
        String commitString = readFile("D:\\java\\projectf\\SpringBootDemo\\SpringBootDemo\\src\\main\\java\\com\\example\\springbootdemo\\repository\\socketio\\commits_data2.txt");
        List<JSONObject> commits = JSONArray.parseArray(commitString, JSONObject.class);
        ArrayList<Integer> commits_between = new ArrayList<>();

        try {
            for (int i = 0; i < releases.size(); i++) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String release_time = releases.get(i).get("published_at").toString().replaceAll("[a-zA-Z]", " ");
                Date release_date = simpleDateFormat.parse(release_time);

                int commit_number = 0;
                for (JSONObject commit : commits) {
                    String commit_time = commit.getJSONObject("commit").getJSONObject("author").get("date").toString().replaceAll("[a-zA-Z]", " ");
                    Date commit_date = simpleDateFormat.parse(commit_time);
                    if (i == 0) {
                        if (commit_date.after(release_date)) {
                            commit_number++;
                        }
                    } else if (i == releases.size() - 1) {
                        if (commit_date.before(release_date)) {
                            commit_number++;
                        }
                    } else {
                        String last_release_time = releases.get(i + 1).get("published_at").toString().replaceAll("[a-zA-Z]", " ");
                        Date last_release_date = simpleDateFormat.parse(last_release_time);
                        if (commit_date.before(release_date) && commit_date.after(last_release_date)) {
                            commit_number++;
                        }
                    }

                }
                commits_between.add(commit_number);

                if (i == 0) {
                    commit_number = 0;
                    for (JSONObject commit : commits) {
                        String commit_time = commit.getJSONObject("commit").getJSONObject("author").get("date").toString().replaceAll("[a-zA-Z]", " ");
                        Date commit_date = simpleDateFormat.parse(commit_time);
                        String last_release_time = releases.get(i + 1).get("published_at").toString().replaceAll("[a-zA-Z]", " ");
                        Date last_release_date = simpleDateFormat.parse(last_release_time);
                        if (commit_date.before(release_date) && commit_date.after(last_release_date)) {
                            commit_number++;
                        }
                    }
                    commits_between.add(commit_number);
                }

            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //获取commit季节时段map
        //获取commit周中时段map
        //获取commit一天中时段map
        HashMap<String, Integer> quarter_commits = new HashMap<>();
        HashMap<String, Integer> week_commits = new HashMap<>();
        HashMap<String, Integer> day_commits = new HashMap<>();
        for (JSONObject commit : commits) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String commit_time = commit.getJSONObject("commit").getJSONObject("author").get("date").toString().replaceAll("[a-zA-Z]", " ");
            Date commit_date;
            try {
                commit_date = simpleDateFormat.parse(commit_time);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            //季节
            int commit_Quarter = Integer.parseInt(DateUtil.yearAndQuarter(commit_date).substring(4));
            switch (commit_Quarter) {
                case 1:
                    if (quarter_commits.containsKey("第一季度")) {
                        quarter_commits.put("第一季度", quarter_commits.get("第一季度") + 1);
                    } else {
                        quarter_commits.put("第一季度", 1);
                    }
                    break;
                case 2:
                    if (quarter_commits.containsKey("第二季度")) {
                        quarter_commits.put("第二季度", quarter_commits.get("第二季度") + 1);
                    } else {
                        quarter_commits.put("第二季度", 1);
                    }
                    break;
                case 3:
                    if (quarter_commits.containsKey("第三季度")) {
                        quarter_commits.put("第三季度", quarter_commits.get("第三季度") + 1);
                    } else {
                        quarter_commits.put("第三季度", 1);
                    }
                    break;
                case 4:
                    if (quarter_commits.containsKey("第四季度")) {
                        quarter_commits.put("第四季度", quarter_commits.get("第四季度") + 1);
                    } else {
                        quarter_commits.put("第四季度", 1);
                    }
                    break;
                default:
                    System.out.println("出大问题");
            }

            //一周中的时段
            Calendar cal = Calendar.getInstance();
            cal.setTime(commit_date);
            String weekday;
            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    weekday = "周一";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.TUESDAY:
                    weekday = "周二";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.WEDNESDAY:
                    weekday = "周三";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.THURSDAY:
                    weekday = "周四";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.FRIDAY:
                    weekday = "周五";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.SATURDAY:
                    weekday = "周六";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                case Calendar.SUNDAY:
                    weekday = "周日";
                    if (week_commits.containsKey(weekday)) {
                        week_commits.put(weekday, week_commits.get(weekday) + 1);
                    } else {
                        week_commits.put(weekday, 1);
                    }
                    break;
                default:
                    System.out.println("日期出问题");

            }

            //一天中时间段
            SimpleDateFormat df = new SimpleDateFormat("HH");
            String str = df.format(commit_date);
            int a = Integer.parseInt(str);
            if (a >= 0 && a <= 6) {
                String day = "凌晨";
                if (day_commits.containsKey(day)) {
                    day_commits.put(day, day_commits.get(day) + 1);
                } else {
                    day_commits.put(day, 1);
                }
            }
            if (a > 6 && a < 12) {
                String day = "上午";
                if (day_commits.containsKey(day)) {
                    day_commits.put(day, day_commits.get(day) + 1);
                } else {
                    day_commits.put(day, 1);
                }
            }
            if (a >= 12 && a <= 13) {
                String day = "中午";
                if (day_commits.containsKey(day)) {
                    day_commits.put(day, day_commits.get(day) + 1);
                } else {
                    day_commits.put(day, 1);
                }
            }
            if (a > 13 && a <= 18) {
                String day = "下午";
                if (day_commits.containsKey(day)) {
                    day_commits.put(day, day_commits.get(day) + 1);
                } else {
                    day_commits.put(day, 1);
                }
            }
            if (a > 18 && a <= 24) {
                String day = "晚上";
                if (day_commits.containsKey(day)) {
                    day_commits.put(day, day_commits.get(day) + 1);
                } else {
                    day_commits.put(day, 1);
                }
            }

        }


        ArrayList<Integer> yData = new ArrayList<>();
        yData.add(quarter_commits.get("第一季度"));
        yData.add(quarter_commits.get("第二季度"));
        yData.add(quarter_commits.get("第三季度"));
        yData.add(quarter_commits.get("第四季度"));

        ArrayList<Integer> week = new ArrayList<>();
        week.add(week_commits.get("周一"));
        week.add(week_commits.get("周二"));
        week.add(week_commits.get("周三"));
        week.add(week_commits.get("周四"));
        week.add(week_commits.get("周五"));
        week.add(week_commits.get("周六"));
        week.add(week_commits.get("周日"));


        ArrayList<Integer> day = new ArrayList<>();
        day.add(day_commits.get("上午"));
        day.add(day_commits.get("中午"));
        day.add(day_commits.get("下午"));
        day.add(day_commits.get("晚上"));
        day.add(day_commits.get("凌晨"));


        ArrayList<Object> list = new ArrayList<>();
        list.add(releases.size());
        list.add(commits_between);
        list.add(yData);
        list.add(week);
        list.add(day);
        return list;
    }



}
