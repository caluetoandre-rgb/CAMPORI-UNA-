package com.example.data.repository

import com.example.R
import com.example.data.model.Announcement
import com.example.data.model.BibleBook
import com.example.data.model.BibleVerse
import com.example.data.model.CamporiMapPoint
import com.example.data.model.GalleryItem
import com.example.data.model.PathfinderIdeal
import com.example.data.model.Registration
import com.example.data.model.ScheduleItem

object CamporiInitialData {

    val ideals = listOf(
        PathfinderIdeal(
            title = "Voto do Desbravador",
            subtitle = "Compromisso de Vida",
            content = "Pela graça de Deus, serei puro, bondoso e leal; guardarei a Lei dos Desbravadores, serei servo de Deus e amigo de todos.",
            meaning = "Expressa a dependência diária da graça divina para viver com integridade, bondade e amor ao próximo."
        ),
        PathfinderIdeal(
            title = "A Lei do Desbravador",
            subtitle = "Os 8 Princípios",
            content = "A Lei do Desbravador ordena-me:\n1. Observar a devoção matinal;\n2. Cumprir fielmente a parte que me corresponde;\n3. Cuidar de meu corpo;\n4. Manter a vista franca;\n5. Ser cortês e obediente;\n6. Andar com reverência na casa de Deus;\n7. Ter um cântico no coração;\n8. Ir aonde Deus mandar.",
            meaning = "Guia prático para a saúde espiritual, física, moral e relacional de cada desbravador."
        ),
        PathfinderIdeal(
            title = "Lema do Campori UNA",
            subtitle = "28 Dez 2026 - 03 Jan 2027 | Malanje",
            content = "MAIS QUE UM LENÇO, UMA MISSÃO!",
            meaning = "O lenço amarelo que usamos no pescoço não é apenas um adereço, mas o símbolo sagrado de um chamado missionário para proclamar o evangelho em Angola e no mundo."
        ),
        PathfinderIdeal(
            title = "Alvo do Desbravador",
            subtitle = "Nossa Meta",
            content = "A mensagem do Advento a todo o mundo em minha geração.",
            meaning = "A determinação juvenil de levar as boas novas da salvação a cada canto da Terra."
        ),
        PathfinderIdeal(
            title = "Objetivo",
            subtitle = "Propósito Fundamental",
            content = "Salvar do pecado e guiar no serviço.",
            meaning = "Cristo no centro de todas as atividades, acampamentos, especialidades e pioneirias."
        ),
        PathfinderIdeal(
            title = "Voto de Fidelidade à Bíblia",
            subtitle = "Palavra Eterna",
            content = "Prometo fidelidade à Bíblia, a sua mensagem de um Salvador crucificado, ressurreto e prestes a vir, doador de vida e liberdade a todos que n'Ele crêem.",
            meaning = "A Bíblia Sagrada como a única regra de fé e prática para a vida diária."
        )
    )

    val hymnLyrics = """
        Nós somos os desbravadores,
        Os servos do Rei dos reis!
        Sempre avante, assim marchamos,
        Fiéis às Suas santas leis.
        
        Preguemos as novas de salvação,
        A todo o mundo com fervor;
        Que breve virá Jesus,
        Nosso querido Redentor!
    """.trimIndent()

    val defaultAnnouncements = listOf(
        Announcement(
            id = 1L,
            title = "Portal de Inscrições Oficial Aberto!",
            summary = "Garanta a inscrição do seu Clube e receba o Crachá Digital com QR Code.",
            body = "A Direção dos Desbravadores da União Nordeste de Angola (UNA) convoca todos os Clubes das Missões Sul de Luanda e Cabinda, Nordeste de Angola, Norte de Angola e Leste de Angola para realizarem o credenciamento oficial. O evento ocorrerá de 28 de Dezembro de 2026 a 03 de Janeiro de 2027 no solo histórico de Pungo a Ndongo, Província de Malanje.",
            dateLabel = "Oficial UNA",
            priority = "Urgente",
            department = "Direção Geral UNA",
            isRead = false
        ),
        Announcement(
            id = 2L,
            title = "Mega Trilha nas Pedras Negras de Pungo Andongo",
            summary = "Orientações de segurança, calçado e hidratação para o 4º dia.",
            body = "A grande caminhada pelas misteriosas Pedras Negras de Pungo a Ndongo será um dos pontos altos do Campori! Cada clube deve preparar suas cantis, chapéus/bonés, protetor solar e kit de primeiros socorros de unidade. Guias locais e desbravadores pioneiros demarcaram as trilhas com segurança total.",
            dateLabel = "Comissão Técnica",
            priority = "Importante",
            department = "Pioneiria & Aventura",
            isRead = false
        ),
        Announcement(
            id = 3L,
            title = "Manual de Concursos e Avaliação 5 Estrelas",
            summary = "Critérios de pontuação: Portais, Nós & Amarras, Ordem Unida e Fanfarra.",
            body = "Para conquistar o padrão 'Clube 5 Estrelas da UNA', todos os diretores devem verificar o regulamento dos portais sem pregos (100% amarras de sisal e eucalipto/bambu), inspeção de acampamento higiênico, civismo matinal e participação no Grande Batismo do Campori.",
            dateLabel = "Secretaria do Campo",
            priority = "Geral",
            department = "Secretaria",
            isRead = false
        ),
        Announcement(
            id = 4L,
            title = "Posto Médico e Triagem 24 Horas em Malanje",
            summary = "Equipe de médicos, enfermeiros e paramédicos da Associação Adventista.",
            body = "O Posto Médico central estará localizado próximo à Arena Principal com ambulância de prontidão, soros, repelentes e atendimento pediátrico. Lembrete: Todos os inscritos com grupo sanguíneo cadastrado no app terão triagem imediata via Crachá Digital.",
            dateLabel = "Saúde & Emergência",
            priority = "Importante",
            department = "Posto Médico",
            isRead = false
        )
    )

    val defaultSchedules = listOf(
        // Dia 1: 28/12/2026
        ScheduleItem(
            id = 101L,
            dayNumber = 1,
            dateLabel = "Segunda, 28 Dez 2026",
            timeLabel = "08:00 - 15:00",
            title = "Chegada das Delegações & Montagem dos Portais",
            description = "Recepção na Secretaria Geral, credenciamento via QR Code, alocação dos Subcampos por Missão e montagem dos portais pioneiros.",
            location = "Secretaria & Subcampos das Missões",
            category = "Pioneiria"
        ),
        ScheduleItem(
            id = 102L,
            dayNumber = 1,
            dateLabel = "Segunda, 28 Dez 2026",
            timeLabel = "16:30 - 18:00",
            title = "Inspeção Inicial de Segurança dos Acampamentos",
            description = "Verificação de tendas, valas de drenagem, mastros de bandeira e extintores de fogo ecológicos.",
            location = "Área de Acampamento",
            category = "Civismo"
        ),
        ScheduleItem(
            id = 103L,
            dayNumber = 1,
            dateLabel = "Segunda, 28 Dez 2026",
            timeLabel = "19:30 - 21:30",
            title = "Abertura Solene: 'Mais que um Lenço, Uma Missão'",
            description = "Entrada das Bandeiras das Províncias de Angola, Hino Nacional e dos Desbravadores, Tocha do Campori e Mensagem Pastoral Inaugural.",
            location = "Arena Principal UNA",
            category = "Cerimônia"
        ),
        // Dia 2: 29/12/2026
        ScheduleItem(
            id = 201L,
            dayNumber = 2,
            dateLabel = "Terça, 29 Dez 2026",
            timeLabel = "06:00 - 07:00",
            title = "Alvorada & Devoção Matinal de Unidade",
            description = "Despertar com toque de clarim, estudo do Ano Bíblico e meditação nas tendas.",
            location = "Subcampos",
            category = "Espiritual"
        ),
        ScheduleItem(
            id = 202L,
            dayNumber = 2,
            dateLabel = "Terça, 29 Dez 2026",
            timeLabel = "08:30 - 12:00",
            title = "Carrossel de Especialidades da Savana",
            description = "Aulas práticas de Nós & Amarras, Orientação por Bússola e GPS, Primeiros Socorros no Campo, Botânica Angolana e Astronomia.",
            location = "Tendas de Oficinas",
            category = "Pioneiria"
        ),
        ScheduleItem(
            id = 203L,
            dayNumber = 2,
            dateLabel = "Terça, 29 Dez 2026",
            timeLabel = "14:30 - 17:30",
            title = "Grande Jogo da Savana de Malanje",
            description = "Desafio cooperativo entre clubes com pistas temáticas bíblicas, resgate em maca artesanal e código morse.",
            location = "Pista de Obstáculos",
            category = "Desafios"
        ),
        ScheduleItem(
            id = 204L,
            dayNumber = 2,
            dateLabel = "Terça, 29 Dez 2026",
            timeLabel = "19:30 - 21:00",
            title = "Culto Noturno: O Lenço que Transforma Vidas",
            description = "Louvor com as melhores vozes da UNA, dramatização missionária e apelo bíblico.",
            location = "Arena Principal UNA",
            category = "Espiritual"
        ),
        // Dia 3: 30/12/2026
        ScheduleItem(
            id = 301L,
            dayNumber = 3,
            dateLabel = "Quarta, 30 Dez 2026",
            timeLabel = "08:00 - 12:00",
            title = "Concurso de Ordem Unida & Fanfarras da UNA",
            description = "Apresentação dos pelotões de marcha padrão, evolução sem comando e sinfonia de tambores e cornetas dos clubes.",
            location = "Praça Cívica do Campori",
            category = "Civismo"
        ),
        ScheduleItem(
            id = 302L,
            dayNumber = 3,
            dateLabel = "Quarta, 30 Dez 2026",
            timeLabel = "14:00 - 17:30",
            title = "Feira Cultural das Regiões de Angola & Projetos Sociais",
            description = "Stands das Missões com comidas típicas, artesanato, pioneirismo comunitário e feira missionária.",
            location = "Boulevard das Missões",
            category = "Geral"
        ),
        ScheduleItem(
            id = 303L,
            dayNumber = 3,
            dateLabel = "Quarta, 30 Dez 2026",
            timeLabel = "19:30 - 21:30",
            title = "Noite de Gala dos Talentos Desbravadores",
            description = "Músicas inéditas compostas para o Campori UNA, declamação de poesias e testemunhos de fé.",
            location = "Arena Principal UNA",
            category = "Cerimônia"
        ),
        // Dia 4: 31/12/2026
        ScheduleItem(
            id = 401L,
            dayNumber = 4,
            dateLabel = "Quinta, 31 Dez 2026",
            timeLabel = "07:30 - 13:00",
            title = "Mega Trilha nas Pedras Negras de Pungo a Ndongo",
            description = "Expedição geológica e ecológica pelos monólitos gigantes de Malanje, com reflexão ao topo sobre a Rocha que é Cristo.",
            location = "Monólito Central de Pungo Andongo",
            category = "Desafios"
        ),
        ScheduleItem(
            id = 402L,
            dayNumber = 4,
            dateLabel = "Quinta, 31 Dez 2026",
            timeLabel = "20:00 - 22:30",
            title = "Fogo do Conselho Gigante da Virada",
            description = "Grande fogueira cerimonial, histórias ao redor do fogo, renovação dos votos e canções de gratidão.",
            location = "Arena da Fogueira UNA",
            category = "Cerimônia"
        ),
        ScheduleItem(
            id = 403L,
            dayNumber = 4,
            dateLabel = "Quinta, 31 Dez 2026",
            timeLabel = "23:00 - 00:30",
            title = "Vigília de Passagem de Ano 2026 / 2027",
            description = "Oração de consagração de joelhos na virada do ano, agradecendo a Deus pelo novo ano com o lenço amarelo ao peito.",
            location = "Arena Principal UNA",
            category = "Espiritual"
        ),
        // Dia 5: 01/01/2027
        ScheduleItem(
            id = 501L,
            dayNumber = 5,
            dateLabel = "Sexta, 01 Jan 2027",
            timeLabel = "09:00 - 12:00",
            title = "Olimpíadas Desbravadoras & Jogos de Pioneiros",
            description = "Competições saudáveis: montagem de barraca contra o relógio, corrida de nós, pista de fogo seguro e cabo de guerra de amizade.",
            location = "Campo Poliesportivo",
            category = "Desafios"
        ),
        ScheduleItem(
            id = 502L,
            dayNumber = 5,
            dateLabel = "Sexta, 01 Jan 2027",
            timeLabel = "17:45 - 19:00",
            title = "Pôr do Sol & Recepção Solene do Santo Sábado",
            description = "Uniforme de Gala obrigatório. Todos os milhares de desbravadores formados em ordem para saudar as horas sagradas.",
            location = "Arena Principal UNA",
            category = "Espiritual"
        ),
        ScheduleItem(
            id = 503L,
            dayNumber = 5,
            dateLabel = "Sexta, 01 Jan 2027",
            timeLabel = "19:30 - 21:00",
            title = "Culto Vespertino do Sábado: 'Firmados na Rocha'",
            description = "Mensagem com o Presidente da União Nordeste de Angola e coro geral dos desbravadores.",
            location = "Arena Principal UNA",
            category = "Espiritual"
        ),
        // Dia 6: 02/01/2027
        ScheduleItem(
            id = 601L,
            dayNumber = 6,
            dateLabel = "Sábado, 02 Jan 2027",
            timeLabel = "08:30 - 10:15",
            title = "Escola Sabatina dos Desbravadores",
            description = "Estudo da Lição por unidades, cartas missionárias de Angola e louvor solene.",
            location = "Arena Principal UNA",
            category = "Espiritual"
        ),
        ScheduleItem(
            id = 602L,
            dayNumber = 6,
            dateLabel = "Sábado, 02 Jan 2027",
            timeLabel = "10:30 - 12:30",
            title = "Grande Culto Divino & Batismo do Campori",
            description = "Cerimônia de batismo nas águas de dezenas de juvenis que decidiram entregar a vida a Cristo no Campori.",
            location = "Tanque Batismal Central",
            category = "Espiritual"
        ),
        ScheduleItem(
            id = 603L,
            dayNumber = 6,
            dateLabel = "Sábado, 02 Jan 2027",
            timeLabel = "15:00 - 18:00",
            title = "Mega Investidura de Classes Regulares e Avançadas",
            description = "Investidura solene de Amigo, Companheiro, Pesquisador, Pioneiro, Excursionista, Guia e Líder Master dos Desbravadores.",
            location = "Arena Principal UNA",
            category = "Cerimônia"
        ),
        ScheduleItem(
            id = 604L,
            dayNumber = 6,
            dateLabel = "Sábado, 02 Jan 2027",
            timeLabel = "19:30 - 22:00",
            title = "Festa Espiritual de Encerramento do Sábado & Louvor",
            description = "Show com cantores adventistas de Angola, desfile de luzes e celebração fraternal.",
            location = "Arena Principal UNA",
            category = "Cerimônia"
        ),
        // Dia 7: 03/01/2027
        ScheduleItem(
            id = 701L,
            dayNumber = 7,
            dateLabel = "Domingo, 03 Jan 2027",
            timeLabel = "08:00 - 10:30",
            title = "Cerimônia de Premiação & Troféus 'Clube 5 Estrelas'",
            description = "Entrega de estandartes, troféus de participação, medalhas de honra ao mérito e proclamação dos campeões.",
            location = "Arena Principal UNA",
            category = "Cerimônia"
        ),
        ScheduleItem(
            id = 702L,
            dayNumber = 7,
            dateLabel = "Domingo, 03 Jan 2027",
            timeLabel = "11:00 - 14:00",
            title = "Desmontagem dos Acampamentos & Regresso Seguro",
            description = "Operação 'Acampamento Limpo' (nenhum lixo deixado para trás), bênção pastoral de viagem e regresso às províncias.",
            location = "Subcampos & Portão Principal",
            category = "Geral"
        )
    )

    val mapPoints = listOf(
        CamporiMapPoint(
            id = "arena",
            title = "Arena Principal & Palco UNA",
            subtitle = "Capacidade: 15.000 Desbravadores",
            zone = "Arena",
            xPercent = 0.50f,
            yPercent = 0.40f,
            iconType = "stage",
            description = "Palco principal coberto, telões LED gigantes, púlpito dos cultos matinais e noturnos, batistério e cerimônias de investidura.",
            openingHours = "06:00 - 23:00",
            coordinator = "Pr. Líder de Jovens UNA"
        ),
        CamporiMapPoint(
            id = "secretaria",
            title = "Secretaria Geral & Credenciamento",
            subtitle = "Portão de Acesso Principal",
            zone = "Serviços",
            xPercent = 0.15f,
            yPercent = 0.85f,
            iconType = "flag",
            description = "Local de conferência de inscrições, emissão de crachás físicos/digitais, entrega de manuais, camisas oficiais e credenciais de liderança.",
            openingHours = "24 Horas",
            coordinator = "Secretária Executiva UNA"
        ),
        CamporiMapPoint(
            id = "posto_medico",
            title = "Posto Médico Central & Primeiros Socorros",
            subtitle = "Ambulatório Móvel & UTI de Suporte",
            zone = "Serviços",
            xPercent = 0.35f,
            yPercent = 0.42f,
            iconType = "medical",
            description = "Equipe médica de plantão com enfermeiros, médicos voluntários, medicamentos de emergência, soros e ambulância 4x4.",
            openingHours = "24 Horas / Emergência",
            coordinator = "Dra. Médica Chefe UNA"
        ),
        CamporiMapPoint(
            id = "subcampo_sul_luanda_cabinda",
            title = "Subcampo Missão Sul de Luanda e Cabinda",
            subtitle = "Acomodações & Portais dos Clubes",
            zone = "Subcampo",
            xPercent = 0.20f,
            yPercent = 0.22f,
            iconType = "tent",
            description = "Área reservada para as delegações da Missão Sul de Luanda e Cabinda. Portais decorados e barracas padronizadas.",
            openingHours = "Acesso restrito à delegação",
            coordinator = "Coordenador Regional Sul de Luanda e Cabinda"
        ),
        CamporiMapPoint(
            id = "subcampo_norte",
            title = "Subcampo Missão Norte de Angola",
            subtitle = "Acomodações & Portais dos Clubes",
            zone = "Subcampo",
            xPercent = 0.35f,
            yPercent = 0.25f,
            iconType = "tent",
            description = "Área reservada para as delegações da Missão Norte de Angola (Bengo, Uíge, Zaire e regiões norte). Portais decorados.",
            openingHours = "Acesso restrito à delegação",
            coordinator = "Coordenador Regional Norte"
        ),
        CamporiMapPoint(
            id = "subcampo_nordeste",
            title = "Subcampo Missão Nordeste de Angola (Anfitriã)",
            subtitle = "Malanje, Cuanza Norte, Lunda Norte, Lunda Sul",
            zone = "Subcampo",
            xPercent = 0.75f,
            yPercent = 0.25f,
            iconType = "tent",
            description = "Área da missão anfitriã do Campori UNA, abrigando centenas de clubes da província de Malanje e região.",
            openingHours = "Acesso restrito à delegação",
            coordinator = "Coordenador Regional Nordeste"
        ),
        CamporiMapPoint(
            id = "subcampo_leste",
            title = "Subcampo Missão Leste de Angola",
            subtitle = "Acomodações & Portais dos Clubes",
            zone = "Subcampo",
            xPercent = 0.88f,
            yPercent = 0.28f,
            iconType = "tent",
            description = "Área reservada para as delegações da Missão Leste de Angola (Moxico, Saurimo e leste). Portais decorados.",
            openingHours = "Acesso restrito à delegação",
            coordinator = "Coordenador Regional Leste"
        ),
        CamporiMapPoint(
            id = "pioneiria",
            title = "Área de Pioneiria & Concursos de Nós",
            subtitle = "Oficinas Práticas & Torneios",
            zone = "Pioneiria",
            xPercent = 0.65f,
            yPercent = 0.65f,
            iconType = "compass",
            description = "Espaço ao ar livre para oficinas de fogos primitivos, nós e amarras, pontes suspensas de corda e torres de observação.",
            openingHours = "08:00 - 17:30",
            coordinator = "Líder Master de Pioneiria"
        ),
        CamporiMapPoint(
            id = "obstaculos",
            title = "Pista de Obstáculos & Sobrevivência",
            subtitle = "Grande Desafio Desbravador",
            zone = "Pioneiria",
            xPercent = 0.85f,
            yPercent = 0.55f,
            iconType = "tree",
            description = "Circuito de desafios físicos e mentais: rastejo militar na lama, tirolesa ecológica, travessia de troncos e orientação por bússola.",
            openingHours = "14:00 - 18:00",
            coordinator = "Equipe de Desafios da UNA"
        ),
        CamporiMapPoint(
            id = "alimentacao",
            title = "Praça de Alimentação & Cozinha Central",
            subtitle = "Refeitório e Apoio Nutricional",
            zone = "Serviços",
            xPercent = 0.40f,
            yPercent = 0.70f,
            iconType = "food",
            description = "Espaço sanitizado para distribuição de suprimentos, água mineral filtrada e cozinhas comunitárias organizadas por clube.",
            openingHours = "06:30 - 21:00",
            coordinator = "Chef de Logística Alimentar"
        ),
        CamporiMapPoint(
            id = "balnearios",
            title = "Complexo de Balneários & Água Potável",
            subtitle = "Higiene Pessoal & Banheiros Sanitizados",
            zone = "Serviços",
            xPercent = 0.20f,
            yPercent = 0.55f,
            iconType = "water",
            description = "Blocos de chuveiros ecológicos e lavatórios masculinos e femininos com abastecimento contínuo de água tratada.",
            openingHours = "05:00 - 22:30",
            coordinator = "Comissão de Infraestrutura"
        ),
        CamporiMapPoint(
            id = "pedras_negras",
            title = "Mirante das Pedras Negras de Pungo Andongo",
            subtitle = "Ponto Turístico & Histórico de Malanje",
            zone = "Natureza",
            xPercent = 0.78f,
            yPercent = 0.85f,
            iconType = "tree",
            description = "Ponto de observação das gigantescas formações rochosas milenares de Pungo a Ndongo, terra da Rainha Ginga. Trilha sinalizada.",
            openingHours = "07:00 - 17:00 com guias",
            coordinator = "Guias Ambientais de Malanje"
        )
    )

    val galleryItems = listOf(
        GalleryItem(
            id = "pungo_1",
            title = "Pedras Negras de Pungo a Ndongo",
            subtitle = "Monólitos Rochosos de Malanje",
            description = "As misteriosas e imponentes rochas gigantescas que se erguem na savana angolana. Palco sagrado e inspirador onde acontecerá a Mega Trilha do II Campori UNA.",
            drawableRes = R.drawable.img_pungo_andongo_1,
            category = "Pedras Negras",
            location = "Pungo a Ndongo, Malanje, Angola"
        ),
        GalleryItem(
            id = "campori_banner",
            title = "II Campori de Desbravadores UNA",
            subtitle = "Mais que um Lenço, Uma Missão",
            description = "Identidade visual oficial do evento. Milhares de lenços amarelos unidos para celebrar a fé, o civismo e o amor a Deus em terras malanjinas.",
            drawableRes = R.drawable.img_campori_banner,
            category = "Acampamento",
            location = "Arena do Campori, Malanje"
        ),
        GalleryItem(
            id = "pungo_2",
            title = "Trilhas Ecológicas e Savana",
            subtitle = "Natureza Selvagem e Preservada",
            description = "Percursos cercados por acácias e formações rochosas onde os desbravadores aprenderão botânica, astronomia e orientação pelas estrelas.",
            drawableRes = R.drawable.img_pungo_andongo_2,
            category = "Pedras Negras",
            location = "Trilha dos Pioneiros, Pungo a Ndongo"
        ),
        GalleryItem(
            id = "kalandula",
            title = "Quedas de Kalandula - Malanje",
            subtitle = "Maravilha Natural de Angola",
            description = "As majestosas cataratas de Kalandula, uma das maiores quedas d'água de África, no Rio Lucala. Excursão especial para líderes e desbravadores destaques.",
            drawableRes = R.drawable.img_kalandula_malange,
            category = "Kalandula",
            location = "Kalandula, Província de Malanje"
        )
    )

    val bibleBooks = listOf(
        BibleBook("Gênesis", "Gn", "Antigo Testamento", 50),
        BibleBook("Êxodo", "Êx", "Antigo Testamento", 40),
        BibleBook("Salmos", "Sl", "Antigo Testamento", 150),
        BibleBook("Provérbios", "Pv", "Antigo Testamento", 31),
        BibleBook("Isaías", "Is", "Antigo Testamento", 66),
        BibleBook("Mateus", "Mt", "Novo Testamento", 28),
        BibleBook("Marcos", "Mc", "Novo Testamento", 16),
        BibleBook("Lucas", "Lc", "Novo Testamento", 24),
        BibleBook("João", "Jo", "Novo Testamento", 21),
        BibleBook("Atos", "At", "Novo Testamento", 28),
        BibleBook("Romanos", "Rm", "Novo Testamento", 16),
        BibleBook("Filipenses", "Fp", "Novo Testamento", 4),
        BibleBook("1 Timóteo", "1Tm", "Novo Testamento", 6),
        BibleBook("Apocalipse", "Ap", "Novo Testamento", 22)
    )

    val keyBibleVerses = listOf(
        BibleVerse("Salmos", 23, 1, "O Senhor é o meu pastor; nada me faltará."),
        BibleVerse("Salmos", 23, 2, "Deitar-me faz em verdes pastos, guia-me mansamente a águas tranqüilas."),
        BibleVerse("Salmos", 23, 3, "Refrigera a minha alma; guia-me pelas veredas da justiça, por amor do seu nome."),
        BibleVerse("Salmos", 23, 4, "Ainda que eu andasse pelo vale da sombra da morte, não temeria mal algum, porque tu estás comigo; a tua vara e o teu cajado me consolam."),
        BibleVerse("Salmos", 119, 105, "Lâmpada para os meus pés é tua palavra e luz, para o meu caminho."),
        BibleVerse("Provérbios", 3, 5, "Confia no Senhor de todo o teu coração e não te estribes no teu próprio entendimento."),
        BibleVerse("Provérbios", 3, 6, "Reconhece-o em todos os teus caminhos, e ele endireitará as tuas veredas."),
        BibleVerse("Isaías", 40, 31, "Mas os que esperam no Senhor renovarão as suas forças; subirão com asas como águias; correrão, e não se cansarão; caminharão, e não se fatigarão."),
        BibleVerse("Mateus", 28, 19, "Portanto ide, fazei discípulos de todas as nações, batizando-os em nome do Pai, e do Filho, e do Espírito Santo;"),
        BibleVerse("Mateus", 28, 20, "Ensinando-os a guardar todas as coisas que eu vos tenho mandado; e eis que eu estou convosco todos os dias, até a consumação dos séculos. Amém."),
        BibleVerse("João", 3, 16, "Porque Deus amou o mundo de tal maneira que deu o seu Filho unigênito, para que todo aquele que nele crê não pereça, mas tenha a vida eterna."),
        BibleVerse("João", 14, 1, "Não se turbe o vosso coração; credes em Deus, crede também em mim."),
        BibleVerse("João", 14, 2, "Na casa de meu Pai há muitas moradas; se não fosse assim, eu vo-lo teria dito. Vou preparar-vos lugar."),
        BibleVerse("João", 14, 3, "E quando eu for, e vos preparar lugar, virei outra vez, e vos levarei para mim mesmo, para que onde eu estiver estejais vós também."),
        BibleVerse("João", 14, 6, "Disse-lhe Jesus: Eu sou o caminho, e a verdade e a vida; ninguém vem ao Pai, senão por mim."),
        BibleVerse("Romanos", 8, 28, "E sabemos que todas as coisas concorrem para o bem daqueles que amam a Deus, daqueles que são chamados segundo o seu propósito."),
        BibleVerse("Romanos", 8, 38, "Porque estou certo de que, nem a morte, nem a vida, nem os anjos, nem os principados, nem as potestades, nem o presente, nem o porvir,"),
        BibleVerse("Romanos", 8, 39, "Nem a altura, nem a profundidade, nem alguma outra criatura nos poderá separar do amor de Deus, que está em Cristo Jesus nosso Senhor."),
        BibleVerse("Filipenses", 4, 13, "Posso todas as coisas em Cristo que me fortalece."),
        BibleVerse("1 Timóteo", 4, 12, "Ninguém despreze a tua mocidade; mas sê o exemplo dos fiéis, na palavra, no trato, no amor, no espírito, na fé, na pureza."),
        BibleVerse("Apocalipse", 14, 6, "E vi outro anjo voar pelo meio do céu, e tinha o evangelho eterno, para o proclamar aos que habitam sobre a terra, e a toda a nação, e tribo, e língua, e povo,"),
        BibleVerse("Apocalipse", 14, 7, "Dizendo com grande voz: Temei a Deus, e dai-lhe glória; porque é chegada a hora do seu juízo. E adorai aquele que fez o céu, e a terra, e o mar, e as fontes das águas."),
        BibleVerse("Apocalipse", 21, 4, "E Deus limpará de seus olhos toda a lágrima; e não haverá mais morte, nem pranto, nem clamor, nem dor; porque já as primeiras coisas são passadas.")
    )

    val sampleRegistrations = listOf(
        Registration(
            id = 1L,
            fullName = "André Calueto",
            clubName = "Estrela da Savana",
            churchName = "Igreja Central de Malanje",
            mission = "Missão Nordeste de Angola",
            region = "1ª Região - Malanje Central",
            role = "Diretor",
            age = 24,
            phone = "+244 923 456 789",
            bloodType = "O+",
            emergencyContact = "+244 912 345 678 (Família Calueto)",
            registrationCode = "UNA-2026-0891",
            status = "Confirmado"
        ),
        Registration(
            id = 2L,
            fullName = "Esperança Manuel",
            clubName = "Sentinelas de Cabinda",
            churchName = "Igreja Central de Cabinda",
            mission = "Missão Sul de Luanda e Cabinda",
            region = "1ª Região - Cabinda",
            role = "Capitão",
            age = 15,
            phone = "+244 944 112 233",
            bloodType = "A+",
            emergencyContact = "+244 933 998 877 (Mãe Teresa)",
            registrationCode = "UNA-2026-1042",
            status = "Confirmado"
        ),
        Registration(
            id = 3L,
            fullName = "Mateus João Domingos",
            clubName = "Pioneiros da Fé",
            churchName = "Igreja Central de Luanda",
            mission = "Missão Norte de Angola",
            region = "5ª Região - Luanda Norte",
            role = "Desbravador",
            age = 13,
            phone = "+244 921 556 677",
            bloodType = "B+",
            emergencyContact = "+244 919 887 766 (Pai João)",
            registrationCode = "UNA-2026-2155",
            status = "Confirmado"
        ),
        Registration(
            id = 4L,
            fullName = "Teresa Chitumba",
            clubName = "Guerreiros do Leste",
            churchName = "Igreja Central de Luena",
            mission = "Missão Leste de Angola",
            region = "1ª Região - Moxico",
            role = "Conselheiro",
            age = 21,
            phone = "+244 926 778 899",
            bloodType = "O+",
            emergencyContact = "+244 918 223 344 (Família Chitumba)",
            registrationCode = "UNA-2026-3410",
            status = "Confirmado"
        )
    )
}
