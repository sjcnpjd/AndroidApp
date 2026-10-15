# ChargeCheck 1.0.3 다운로드

## Windows Android Studio용 소스 프로젝트

- [최종 소스 프로젝트 ZIP 다운로드](./ChargeCheck-1.0.3-source.zip?raw=true)
- 출시 앱 ID: `com.sjcnpjd.chargecheck`
- 버전: 1.0.3 / versionCode 4

소스 ZIP은 AES-256으로 암호화했습니다. 압축 암호는 소유자와의 대화에서만 전달하며 이 공개 저장소에는 게시하지 않습니다. Windows 기본 압축 기능이 지원하지 않으면 [7-Zip 공식 사이트](https://www.7-zip.org/)의 Windows용 프로그램으로 압축을 푸세요.

압축 해제 후 Android Studio에서 **Open**을 누르고 `settings.gradle.kts`, `gradlew.bat`, `app` 폴더가 들어 있는 `ChargeCheck` 폴더를 여세요. 새 빈 프로젝트를 만들 필요가 없습니다. 폴더의 `WINDOWS_START_HERE_KO.md`에 JDK/SDK 설정과 서명 AAB 만드는 순서를 적었습니다. 소유자의 서명 키나 비밀번호는 포함하지 않았습니다. 원본 마스터 명세서, 이전 Food 앱, Git 이력, 로컬 설정과 빌드 캐시도 제외했습니다.

## 휴대전화 설치용 테스트 APK

- [APK 직접 다운로드](./ChargeCheck-1.0.3-debug.apk?raw=true)
- [같은 APK의 일반 ZIP 다운로드](./ChargeCheck-debug.zip?raw=true)

Android 8.0 이상. 현재 APK의 설치 ID는 `com.sjcnpjd.chargecheck.debug`입니다. 이전 `com.cookandroid.food.debug` 테스트 앱과 별도로 설치되며 이전 기록은 자동 이전되지 않습니다. 테스트 APK는 Play 제출용 AAB가 아닙니다. debug 광고는 Google 공식 테스트 ID를 사용하고 출시용 소스에는 소유자 제공 배너 설정을 유지했습니다.

이번 변경 후 debug APK, R8 최적화 출시 AAB, 포맷 검사와 API 26/28/34/36의 프레임워크 테스트 12개가 통과했습니다. 새 앱 ID, 실행 클래스, 공유 FileProvider 주소를 확인했습니다. 앞선 동일 기능 버전에서는 141개 자동 테스트와 11가지 UI 조건의 호스트 렌더링을 통과했습니다. 실제 추가 OEM, 광고/동의 SDK 송출, 소유자 서명과 출시 빌드 실기기 검증은 남아 있습니다.

이 브랜치에는 설치 APK, 일반 APK ZIP, 암호화된 소스 ZIP과 이 안내문만 있습니다. 복호화한 소스나 압축 암호를 공개하지 않습니다.

## 파일 검증

Source revision: `0ccdcd7da1233472912a666f63f35e738a609147`

- ChargeCheck-1.0.3-debug.apk: `dab3de510fe46d8588d4568a5fa5af5cbce599fa09f5fb4d804ac6cdd51a9972`
- ChargeCheck-debug.zip: `cbb9957dc634640596d408666b65af0a11734e023a976aee854c062100bd2f49`
- ChargeCheck-1.0.3-source.zip: `e8bf58173b4abdab1eae6792340a20aa67e644fee3a8680cbfce88a598ba69df`
