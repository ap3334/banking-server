# ERD
<img width="688" alt="스크린샷 2023-03-02 오후 4 17 57" src="https://user-images.githubusercontent.com/62919440/222357977-e209b55a-d8ec-48f4-a76a-6009cffc7215.png">


---

### v1.
![Untitled (3)](https://user-images.githubusercontent.com/62919440/222357323-201eb73f-082d-430e-a02c-c46c9be89ae5.png)

### v2.

**고려한 점**
- 한명의 사용자당 한개의 계좌를 만들 것이므로 one to one 관계로 정의했다
- 그러나 실제 뱅킹서버는 한 명의 사용자 당 여러 계좌를 개설할 수 있으므로 확장성을 고려하면 one-to-many로 변경하는 것이 좋을 것 같다 → 그러나 단순한 계좌 송금만 구현할 것이므로 one-to-one으로 진행한다
- 사용자 한 명당 친구 관계가 존재하지 않거나 하나 이상일 수 있으므로 one-to-many로 정의하고 친구 관계에 해당하는 사용자들의 id를 외래키로 설정한다

![Untitled (4)](https://user-images.githubusercontent.com/62919440/222357385-7126dc86-ca00-4632-aaf0-0e4bcdd6fa60.png)

### v3.

**고려한 점**
- 확장성을 위해서 계좌이체시 거래내역을 담는 엔티티를 추가했다.(지금은 계좌 조회시 총 금액밖에 안나오지만 거래 내역까지 보여줄 수 있게 확장가능)
- 친구 추가 시 양방향 친구 추가 아닌 단방향으로만 추가하게 변경(양방향 친구 추가 시에는 요청, 응답 등 비즈니스 로직이 복잡하게 들어가야해서 변경, 추후에 확장 가능)

<img width="688" alt="스크린샷 2023-03-02 오후 4 17 57" src="https://user-images.githubusercontent.com/62919440/222357991-3ed4437f-dd1a-482f-bd83-c1c6fc5576da.png">
