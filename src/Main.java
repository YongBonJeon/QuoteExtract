import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String[] keywords = {"은행", "공사", "캐피탈", "국민", "수출입", "증권", "한국", "카드", "국고채", "롯데", "신한", "유안타",
                "쇼핑", "엘지", "LG", "산금", "농금", "중금", "케미칼", "화학", "주택", "발전", "대신", "기업", "주금공", "우금캐", "국주",
                "하나", "수금", "투자", "IBK", "현대", "산업", "개발", "재단", "KCC", "현대", "가온전선", "하카", "대신", "메리츠", "신카",
                "예특", "신캐", "하캐", "중벤공", "한화", "SK", "NH", "통안", "우금캐", "펄어비스", "우카", "공단", "에스케이", "텔레콤"};

        Map<String, String> categoryMap = new HashMap<>();
        registerCategory(categoryMap);

        File rtext = new File("채권 호가 정리.txt");
        File wtext_sell = new File("팔자,매도 결과 정리.txt");
        File wtext_buy = new File("사자,매수 결과 정리.txt");
        File wtext_FRN = new File("FRN 결과 정리.txt");
        File wtext_replace = new File("교체 결과 정리.txt");

        FileReader reader = new FileReader(rtext);
        FileWriter writer_sell = new FileWriter(wtext_sell);
        FileWriter writer_buy = new FileWriter(wtext_buy);
        FileWriter writer_FRN = new FileWriter(wtext_FRN);
        FileWriter writer_replace = new FileWriter(wtext_replace);

        BufferedReader bufferedReader = new BufferedReader(reader);

        List<String> FRN = new ArrayList<>();
        List<String> replace = new ArrayList<>();
        List<String> buy = new ArrayList<>();

        // 날짜 포맷 : yy.mm.dd , yy.mm.dd(요일)
        String datePattern = "\\b\\d{2}\\.\\d{1,2}\\.\\d{1,2}\\b";

        Set<sellUnit> sellUnitSet = new HashSet<>();

        // 호가
        String quote;
        String origin;
        while ((quote = bufferedReader.readLine()) != null) {
            // FRN 별도 처리
            if (quote.contains("FRN")) {
                writer_FRN.write(quote.substring(17) + "\n");
            }
            // 사자/매도 처리
            else if (quote.contains("사자") || quote.contains("매수")) {
                buy.add(quote);
                writer_buy.write(quote.substring(17) + "\n");
            }
            // 팔자 처리
            else if (quote.contains("팔자")) {
                String[] s = quote.split("팔자");
                origin = quote;
                quote = s[0];

                // 날짜 추출 
                Pattern pattern = Pattern.compile(datePattern);
                Matcher matcher = pattern.matcher(quote);
                String matchDate = null;
                while (matcher.find()) {
                    matchDate = matcher.group();
                }

                // 회사명 추출
                String matchCompany = null;
                String[] split = quote.split(" ");
                for (String ss : split) {
                    for (String keyword : keywords) {
                        if (ss.contains(keyword)) {
                            Pattern pattern1 = Pattern.compile("[a-z|A-Z|가-힣]+");
                            Matcher matcher1 = pattern1.matcher(ss);

                            if (matcher1.find()) {
                                matchCompany = matcher1.group();
                                break;
                            }
                        }
                    }
                }
                if (matchDate != null && matchCompany != null) {
                    String name = quote.substring(0,3);
                    if(name.matches("[가-힣]+"))
                        sellUnitSet.add(new sellUnit(name, "20" + matchDate, matchCompany, origin.substring(17)));
                }

            }
            // 매도 처리
            else if (quote.contains("매도")) {
                String[] s = quote.split("매도");
                quote = s[0];

                // 날짜 추출
                Pattern pattern = Pattern.compile(datePattern);
                Matcher matcher = pattern.matcher(quote);
                String matchDate = null;
                while (matcher.find()) {
                    matchDate = matcher.group();
                }

                // 회사명 추출
                String matchCompany = null;
                String[] split = quote.split(" ");
                for (String ss : split) {
                    for (String keyword : keywords) {
                        if (ss.contains(keyword)) {
                            Pattern pattern1 = Pattern.compile("[a-z|A-Z|가-힣]+");
                            Matcher matcher1 = pattern1.matcher(ss);

                            if (matcher1.find()) {
                                matchCompany = matcher1.group();
                                break;
                            }
                        }
                    }
                }
                if (matchDate != null && matchCompany != null) {
                    String name = quote.substring(0,3);
                    if(name.matches("[가-힣]+"))
                        sellUnitSet.add(new sellUnit(name, "20" + matchDate, matchCompany, quote.substring(17)));
                }
            } else if (quote.contains("교체")) {
                writer_replace.write(quote.substring(17) + "\n");
            }
        }

        List<sellUnit> sellUnitList = sellUnitSet.stream().toList();

        Map<key, Integer> influenceMap = new HashMap<>();

        for (sellUnit sellUnit : sellUnitList) {
            key key = new key(sellUnit.date, sellUnit.company);
            //System.out.println("sellUnit = " + sellUnit);
            influenceMap.put(key, influenceMap.getOrDefault(key, 0) + 1);
        }

        for (sellUnit sellUnit : sellUnitList) {
            writer_sell.write(categoryMap.getOrDefault(sellUnit.company, "X") + ", " +
                    sellUnit.date + ", " + sellUnit.company + ", " + influenceMap.get(new key(sellUnit.date, sellUnit.company)) + ", " +
                    sellUnit.quote + "\n");
        }
        bufferedReader.close();
        reader.close();
        writer_sell.close();
        writer_replace.close();
        writer_FRN.close();
        writer_buy.close();
    }

    private static void registerCategory(Map<String, String> categoryMap) {
        categoryMap.put("광해공업공단", "특수채 AAA");
        categoryMap.put("한국공항공단", "특수채 AAA");
        categoryMap.put("한국해양진흥공사", "특수채 AAA");
        categoryMap.put("서울교통공사", "특수채 AAA");
        categoryMap.put("한국수자원공사", "특수채 AAA");
        categoryMap.put("울산항만공사", "특수채 AAA");
        categoryMap.put("한국토지주택공사", "특수채 AAA");
        categoryMap.put("인천항만공사", "특수채 AAA");
        categoryMap.put("한국산업단지공단", "특수채 AAA");
        categoryMap.put("부산항만공사", "특수채 AAA");
        categoryMap.put("부산교통공사", "특수채 AAA");
        categoryMap.put("한국철도공사", "특수채 AAA");
        categoryMap.put("제주개발센터", "특수채 AAA");

        categoryMap.put("농협은행", "은행채 AAA");
        categoryMap.put("수협은행", "은행채 AAA");
        categoryMap.put("우리은행", "은행채 AAA");
        categoryMap.put("한국수출입은행", "은행채 AAA");
        categoryMap.put("국민은행", "은행채 AAA");
        categoryMap.put("부산은행", "은행채 AAA");
        categoryMap.put("대구은행", "은행채 AAA");
        categoryMap.put("하나은행", "은행채 AAA");
        categoryMap.put("스탠다드차타드은행", "은행채 AAA");
        categoryMap.put("신한은행", "은행채 AAA");
    }
}