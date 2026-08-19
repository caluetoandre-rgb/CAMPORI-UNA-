# CAMPORI UNA - Aplicativo Oficial
### II Campori de Desbravadores da União Nordeste de Angola (Pungo a Ndongo, Malanje)

Este repositório contém o código-fonte do aplicativo nativo Android do **II Campori UNA**.

---

## 📱 Como Gerar e Baixar o APK no GitHub

Em projetos Android nativos (Kotlin/Gradle), **não existe um arquivo `index.html`** como na web tradicional. O arquivo de entrada do projeto para compilar o APK é o **`build.gradle.kts`** e **`settings.gradle.kts`**.

Configuramos o **GitHub Actions** (`.github/workflows/build-apk.yml`) para compilar e disponibilizar o APK automaticamente no GitHub:

### Opção 1: Baixar o APK gerado pelo GitHub Actions (Mais Fácil)
1. Vá até a aba **"Actions"** no seu repositório do GitHub.
2. Clique no workflow **"Build Android APK (Campori UNA)"** (ou clique em *Run workflow*).
3. Após a conclusão verde (Build), clique na execução e role até a seção **Artifacts**.
4. Baixe o arquivo **`Campori-UNA-Debug-APK`**, descompacte e instale o `.apk` no seu celular Android.

---

### Opção 2: Gerar o APK localmente via Linha de Comando / Terminal
Se você clonou o repositório no seu computador:
```bash
# No Linux / macOS:
./gradlew assembleDebug

# No Windows:
gradlew.bat assembleDebug
```
O arquivo APK gerado estará localizado em:
`app/build/outputs/apk/debug/app-debug.apk`

---

### Opção 3: Abrir no Android Studio
1. Abra o **Android Studio**.
2. Selecione **File > Open** e escolha a pasta deste projeto.
3. Aguarde o Gradle sincronizar.
4. Vá em **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

---

## 🚀 Funcionalidades do App
- **Portal de Inscrição**: Cadastro completo com nome, clube, igreja, missão da UNA, cargo, grupo sanguíneo e contato de emergência.
- **Crachá Digital com QR Code**: Geração imediata de credenciamento oficial.
- **Cronograma Dinâmico**: 7 dias de atividades com filtros por categoria e busca.
- **Mapa Interativo**: Navegação pelos subcampos e instalações de Pungo a Ndongo.
- **Bíblia Sagrada Offline**: Antigo e Novo Testamento com Ano Bíblico e favoritos.
- **Galeria de Fotos**: Pedras Negras de Pungo a Ndongo, Quedas de Kalandula e história de Malanje.
- **Hino & Ideais**: Áudio com a melodia sintetizada do Hino dos Desbravadores e os Ideais oficiais da UNA.
