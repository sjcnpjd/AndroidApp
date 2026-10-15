# ChargeCheck 휴대전화 업데이트

**1.0.2-debug / versionCode 3**, Android 8.0 이상.

- [APK 직접 다운로드](./ChargeCheck-1.0.2-debug.apk?raw=true)
- [ZIP 다운로드](./ChargeCheck-debug.zip?raw=true)

APK를 열어 설치하세요. ZIP을 받았다면 압축을 풀고 `ChargeCheck-1.0.2-debug.apk`를 여세요. 이전 debug APK와 서명이 같아 기존 앱 위에 업데이트할 수 있습니다.

확인된 한국형 Galaxy S24+ SM-S926N / Android 16의 전류 보고 단위를 정규화해 A/W가 1000배 작게 계산되는 오류를 수정했습니다. 원본 API 값과 해석 단위는 진단에 함께 보존합니다. 관련 구버전 결과는 원본을 보존하고 재측정 필요로 표시하며, 잘못된 비교 비율과 공유를 막습니다.

새로 60초 측정한 결과를 확인하세요. 이상하면 앱을 종료하지 말고 홈의 ‘진단 내용 복사’를 눌러 대화에 붙여 주세요. 원본 센서 기록은 자동 업로드하지 않습니다.

자동 테스트 117개, 포맷·번역 검사, Lint 오류 0개, 출시용 최적화 빌드가 통과했습니다. 기존 Lint 경고 12개가 남아 있습니다. 이 파일은 업데이트 테스트용이며, 수정 버전의 실기기 검증과 정식 출시 서명·배포 절차는 남아 있습니다. 전력은 배터리 기준 추정값입니다.

이 브랜치에는 APK, 같은 APK의 ZIP과 이 안내문만 있습니다.

SHA-256:

- APK: `3f9a12c3ead1651ed1adfadc2720630969a6c0be6054d88e266b2a9f697fa74f`
- ZIP: `8a877ee032aef7acff5e4997014abc4f11fb33702347df8c1c9d7dbde48bddf5`
