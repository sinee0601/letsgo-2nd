# Git Commit Message Convention

## 1. 기본 구조 (Template)

```
[타입]: [제목] (50자 이내, 명사형/명령문, 끝에 마침표 없음)

[본문] (생략 가능, '어떻게'보다 '왜' 변경했는지 기술)
```

## 2. 핵심 타입 5가지 (Type)

| 타입 | 설명 |
|------|------|
| `feat` | 새로운 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 수정 (README.md, 주석 등) |
| `style` | 코드 의미에 영향을 주지 않는 변경 (포맷팅, 세미콜론 누락 등) |
| `refactor` | 코드 리팩토링 (기능 변경 없이 코드 구조만 개선) |

## 3. 타입별 작성 예시 (Examples)

### feat
```
feat: 로그인 기능 추가

- Spring Security 기반 JWT 토큰 인증 로직 구현
- 회원가입 API 연동 완료
```

### fix
```
fix: @RestControllerAdvice 예외 처리 오류 수정

- NullPointerException 발생 시 500 에러 대신 400 에러를 반환하던 오류 수정
- GlobalExceptionHandler 내 상태 코드 매핑 정정
```

### docs
```
docs: README.md 빌드 및 실행 방법 추가

- 로컬 환경 구동을 위한 application.yaml 설정 가이드 추가
```

### style
```
style: Service 레이어 코드 인덴트 및 세미콜론 정리

- 기능 변경 없음, IntelliJ 자동 정렬(Ctrl+Alt+L) 적용
```

### refactor
```
refactor: 회원 조회 로직 MyBatis에서 JPA로 전환

- 기존 MemberDAO 클래스를 MemberRepository 구조로 리팩토링
- 가독성 향상 및 쿼리 최적화
```

## 4. 핵심 주의 사항 (Checklist)

- 제목과 본문 사이에는 반드시 **한 줄**을 비워야 인식이 잘 됩니다.
- 제목에 "~ 완료함", "~ 수정했음" 같은 과거형은 지양합니다.
