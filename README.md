- 팔자/매도에 검출되는 기업을 늘리려면 9번째 줄에 있는 keywords 배열에 기업명을 추가하면 된다. 
- 기업의 카테고리를 추가하기 위해선 152번째 줄에 있는 함수에 categoryMap.put("추가할 기업명", "추가할 카테고리")를 작성하면 된다. 
- 실행 시 결과
  - FRN 결과 정리.txt : FRN이 들어가 있는 경우의 호가 전문 출력
  - 교체 결과 정리.txt : 교체가 들어가 있는 경우의 호가 전문 출력
  - 사자, 매수 결과 정리.txt : 사자/매수가 들어가 있는 경우의 호가 전문 출력
  - 팔자, 매도 결과 정리.txt : 팔자/매도가 들어가 있는 경우의 호가에서 데이터 추출 / 호가 하나에서 입력자 / 만기일 / 발행사명을 우선 추출 ->  입력자 / 만기일 / 발행사명이 모두 같은 경우는 중복으로 판단하여 삭제 -> 만기일 / 발행사명이 같고 입력자가 다른 경우 영향력을 증가 -> 최종적으로 발행사 [카테고리 / 만기일 / 발행사명 / 영향력 / 호가 전문]을 출력 
    