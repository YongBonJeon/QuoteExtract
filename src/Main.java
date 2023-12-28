import java.io.*;
import java.util.regex.*;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] keywords = {"은행", "공사", "캐피탈", "국민", "수출입", "증권", "한국", "카드", "국고채", "롯데", "신한", "유안타",
                "쇼핑", "엘지", "LG", "산금", "농금", "중금", "케미칼", "화학", "주택", "발전", "대신", "기업", "주금공", "우금캐", "국주",
                "하나", "수금", "투자", "IBK", "현대", "산업", "개발", "재단", "KCC", "현대", "가온전선", "하카", "대신", "메리츠", "신카",
                "예특", "신캐", "하캐", "중벤공", "한화", "SK", "NH", "통안", "우금캐", "펄어비스", "우카", "공단", "에스케이", "텔레콤"};


        File rtext = new File("채권 호가 정리.txt");
        File wtext1 = new File("팔자/매도 결과 정리.txt");
        File wtext2 = new File("사자/매수 결과 정리.txt");
        File wtext3 = new File("FRN 결과 정리.txt");

        FileReader reader = new FileReader(rtext);
        FileWriter writer1 = new FileWriter(wtext1);
        FileWriter writer2 = new FileWriter(wtext2);
        FileWriter writer3 = new FileWriter(wtext3);

        BufferedReader bufferedReader = new BufferedReader(reader);

        // 날짜 포맷 : yy.mm.dd , yy.mm.dd(요일)
        String datePattern = "\\b\\d{2}\\.\\d{1,2}\\.\\d{1,2}\\b";

        // 호가
        String quote;

        while ((quote = bufferedReader.readLine()) != null) {
            if (quote.contains("FRN")) {
                writer3.write(quote);
            }

            if (quote.contains("사자") || quote.contains("매수")) {
                writer2.write(quote);
            } else if (quote.contains("팔자")) {
                String[] s = quote.split("팔자");
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
                if (matchDate != null && matchCompany != null)
                    writer.write(matchDate + ", " + matchCompany + "\n");
                //System.out.println(matchDate + ", " + matchCompany);
            } else if (quote.contains("매도")) {
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
                if (matchDate != null && matchCompany != null)
                    writer.write(matchDate + ", " + matchCompany + "\n");
                //System.out.println(matchDate + ", " + matchCompany);
            }
        }
    }
}