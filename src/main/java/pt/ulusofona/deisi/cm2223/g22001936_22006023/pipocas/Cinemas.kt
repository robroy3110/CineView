package pt.ulusofona.deisi.cm2223.g22001936_22006023.pipocas

import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Cinema
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Horario
import pt.ulusofona.deisi.cm2223.g22001936_22006023.models.Rating

object Cinemas {
    private val _cinemas = mutableListOf<Cinema>(
        Cinema(90829,
            "Fórum Montijo",
            "Cinemas NOS",
            "",
            38.6949792.toFloat(),
            -8.942057.toFloat(),
            "Praça da Liberdade, 32",
            "2870-461",
            "Montijo",
            mutableListOf(),
            mutableListOf(
                Rating("limpeza de salas", 4),
                Rating("qualidade de som", 1),
                Rating("conforto", 10),
                Rating("qualidade de imagem", 9)
            ),
            mutableListOf(
                Horario("Monday", "14:00", "23:00"),
                Horario("Tuesday", "14:00", "23:00"),
                Horario("Wednesday", "14:00", "23:00"),
                Horario("Thursday", "14:00", "23:00"),
                Horario("Friday", "14:00", "23:00"),
                Horario("Saturday", "14:00", "23:00"),
                Horario("Sunday", "14:00", "23:00")
            )),
        Cinema(id = 90849,
            name = "Cinema Ideal",
            provider = "Cinemateca Portuguesa",
            latitude = 38.71031433095038f,
            longitude = -9.144281436853934f,
            address = "Rua do Loreto, 15",
            postcode = "1200-241",
            county = "Lisboa",
            photos = mutableListOf(
                "https://bolimg.blob.core.windows.net/producao/imagens/entidades/aderentes/ent3129.jpg?v=21",
                "https://www.cinemateca.pt/getattachment/680f9471-3670-481e-b788-a17a3769ee8c/CINEMATECA-ULTIMOS-ESPACOS-REABERTOS.aspx?maxsidesize=650",
                "https://festadocinemaitaliano.com/library/media/257",
                "https://3.bp.blogspot.com/-PZP01-bnZd4/Urfs8M19qQI/AAAAAAAAESc/n00ysuZfOHo/s1600/historia_cinemateca02.jpg",
                "https://portugalfilmcommission.com/wp-content/uploads/2020/12/Cinemateca-portuguesa.jpg"
            ),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 8),
                Rating(category = "qualidade de som", score = 9),
                Rating(category = "conforto", score = 7),
                Rating(category = "qualidade de imagem", score = 9)
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Tuesday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Wednesday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Thursday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Friday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Saturday", openHour = "14:00", closeHour = "22:00"),
                Horario(dia = "Sunday", openHour = "14:00", closeHour = "22:00")
            )),
        Cinema(id = 8579,
            name = "Cinema City Campo Pequeno",
            provider = "Cinema City",
            latitude = 38.74265837959478f,
            longitude = -9.146832184181024f,
            address = "Centro Comercial Campo Pequeno, Piso 2, Loja 410",
            postcode = "1000-082",
            county = "Lisboa",
            photos = mutableListOf(
                "https://media-manager.noticiasaominuto.com/1920/1591017510/naom_5ed4ffe789d8d.jpg?crop_params=eyJsYW5kc2NhcGUiOnsiY3JvcFdpZHRoIjoyNTYwLCJjcm9wSGVpZ2h0IjoxNDQwLCJjcm9wWCI6MCwiY3JvcFkiOjI2Nn19",
                "http://www.campopequeno.com/docs/images/crop/20160118145018498041.jpg",
                "https://www.cinemacity.pt/images/editorials/CAMPO_3.png",
                "https://www.cinemacity.pt/images/editorials/CAMPO_5.png",
                "https://www.cinemacity.pt/images/editorials/CAMPO_4.png",
                "https://www.cinemacity.pt/images/editorials/CAMPO_1.png",
                "https://www.cinemacity.pt/images/editorials/CAMPO_2.png"
            ),
            ratings = listOf(
                Rating("limpeza de salas", 9),
                Rating("qualidade de som", 8),
                Rating("conforto", 9),
                Rating("qualidade de imagem", 8)
            ),
            hours = listOf(
                Horario("Monday", "14:00", "22:00"),
                Horario("Tuesday", "14:00", "22:00"),
                Horario("Wednesday", "14:00", "22:00"),
                Horario("Thursday", "14:00", "22:00"),
                Horario("Friday", "14:00", "23:00"),
                Horario("Saturday", "10:30", "23:00"),
                Horario("Sunday", "10:30", "22:00")
            )),
        Cinema(id = 1234,
            name = "El Corte Inglês",
            provider = "UCI Cinemas",
            latitude = 38.74265803270522f,
            longitude = -9.153398256219777f,
            address = "Av. António Augusto de Aguiar 31, 1069-413",
            postcode = "1069-413",
            county = "Lisboa",
            photos = mutableListOf(
                "https://festadocinemaitaliano.com/library/media/715",
                "http://convida.pt/images/POIs/UCIcinema_ECI_01.jpg",
                "https://www.centralcomics.com/wp-content/uploads/2020/07/IMG-20200710-WA0000.jpg",
                "https://fastly.4sqi.net/img/general/600x600/gkh06Omu9veyltOJ02zdf9Vs3OtG8bDIs7TMiNo1rqY.jpg",
                "https://eco.imgix.net/uploads/2022/02/cropped-fachada-eci-1.jpg",
                "https://site.qmagine.com/qmagine_content/uploads/2020/02/uci_cinemas_el_corte_ingles_lisboa_thumb.jpg"
            ),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 9),
                Rating(category = "qualidade de som", score = 8),
                Rating(category = "conforto", score = 9),
                Rating(category = "qualidade de imagem", score = 8)
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Tuesday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Wednesday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Thursday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Friday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Saturday", openHour = "12:00", closeHour = "23:00"),
                Horario(dia = "Sunday", openHour = "12:00", closeHour = "23:00")
            )),
        Cinema(id = 2589,
            name = "Cinemas NOS Centro Comercial Colombo",
            provider = "Cinemas NOS",
            latitude = 38.75366130000005f,
            longitude = -9.205767760449193f,
            address = "Centro Comercial Colombo, Avenida Lusíada",
            postcode = "1500-392",
            county = "Lisboa",
            photos = mutableListOf(
                "https://lisbonshopping.com/wp-content/uploads/2022/11/LisbonShopping_1600x1200_CinemaColombo_0001_wZnyazd-1000x680.jpg",
                "https://gyptec.eu/wp-content/uploads/2021/03/gyptec_imax1.jpg",
                "https://take.com.pt/wp-content/uploads/2017/07/1-H1lb-Q9sYGubhu2a2rVQYg-730x487.jpeg",
                "https://www.nit.pt/wp-content/uploads/2021/04/73c2a8d86d428177b8da839b09b4b153-754x424.jpeg",
                "https://cinemas.nos.pt/_layouts/15/Handlers/RenderImage.ashx?file=49930.jpg"
            ),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 7),
                Rating(category = "qualidade de som", score = 8),
                Rating(category = "conforto", score = 9),
                Rating(category = "qualidade de imagem", score = 8),
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "13:30", closeHour = "23:00"),
                Horario(dia = "Tuesday", openHour = "13:30", closeHour = "23:00"),
                Horario(dia = "Wednesday", openHour = "13:30", closeHour = "23:00"),
                Horario(dia = "Thursday", openHour = "13:30", closeHour = "23:00"),
                Horario(dia = "Friday", openHour = "13:30", closeHour = "23:00"),
                Horario(dia = "Saturday", openHour = "10:30", closeHour = "23:00"),
                Horario(dia = "Sunday", openHour = "10:30", closeHour = "23:00"),
            )),
        Cinema(id = 9023,
            name = "Cinemas NOS Alvaláxia",
            provider = "Cinemas NOS",
            latitude = 38.76098497959197f,
            longitude = -9.162060684180636f,
            address = "Av. de Roma, 100",
            postcode = "1700-349",
            county = "Lisboa",
            photos = mutableListOf(
                "https://gyptec.eu/wp-content/uploads/2021/03/gyptec_imax1.jpg",
                "https://take.com.pt/wp-content/uploads/2017/07/1-H1lb-Q9sYGubhu2a2rVQYg-730x487.jpeg",
                "https://www.nit.pt/wp-content/uploads/2021/04/73c2a8d86d428177b8da839b09b4b153-754x424.jpeg",
                "https://lh5.googleusercontent.com/p/AF1QipMdKC7Fl0fsGNpj6DJmwjkKejie5DK50KxF0zcg=w280-h226-k-no"
            ),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 9),
                Rating(category = "qualidade de som", score = 8),
                Rating(category = "conforto", score = 7),
                Rating(category = "qualidade de imagem", score = 8)
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "12:30", closeHour = "23:00"),
                Horario(dia = "Tuesday", openHour = "12:30", closeHour = "23:00"),
                Horario(dia = "Wednesday", openHour = "12:30", closeHour = "23:00"),
                Horario(dia = "Thursday", openHour = "12:30", closeHour = "23:00"),
                Horario(dia = "Friday", openHour = "12:30", closeHour = "00:00"),
                Horario(dia = "Saturday", openHour = "11:00", closeHour = "00:00"),
                Horario(dia = "Sunday", openHour = "11:00", closeHour = "23:00")
            )),
        Cinema(id = 9632,
            name = "Cinema São Jorge",
            provider = "EGEAC",
            latitude = 38.7203028795984f,
            longitude = -9.14848308418151f,
            address = "Avenida da Liberdade, 175",
            postcode = "1250-146",
            county = "Lisboa",
            photos = mutableListOf("https://egeac.pt/wp-content/uploads/2021/02/dsc04360jose-frade-20190212-184601-1-1024x683.jpg"),
            ratings = listOf(
                Rating("limpeza de salas", 9),
                Rating("qualidade de som", 8),
                Rating("conforto", 7),
                Rating("qualidade de imagem", 8)
            ),
            hours = listOf(
                Horario("Monday", "14:00", "23:00"),
                Horario("Tuesday", "14:00", "23:00"),
                Horario("Wednesday", "14:00", "23:00"),
                Horario("Thursday", "14:00", "23:00"),
                Horario("Friday", "14:00", "23:00"),
                Horario("Saturday", "14:00", "23:00"),
                Horario("Sunday", "14:00", "23:00")
            )),
        Cinema(82974,
            "Cinema do Parque",
            "Cineclube de Lisboa",
            "",
            38.718222079598625.toFloat(),
            -9.137756484181562.toFloat(),
            "Rua da Palma, 246",
            "1100-394",
            "Lisboa",
            mutableListOf<String>(),
            listOf(
                Rating("limpeza de salas", 9),
                Rating("qualidade de som", 8),
                Rating("conforto", 7),
                Rating("qualidade de imagem", 9)
            ),
            listOf(
                Horario("Monday", "16:00", "23:00"),
                Horario("Tuesday", "16:00", "23:00"),
                Horario("Wednesday", "16:00", "23:00"),
                Horario("Thursday", "16:00", "23:00"),
                Horario("Friday", "16:00", "23:00"),
                Horario("Saturday", "14:00", "23:00"),
                Horario("Sunday", "14:00", "23:00")
            )),
        Cinema(1357,
            "UCI Cinemas Ubbo",
            "UCI Cinemas",
            "",
            38.77588027958963f,
            -9.22224658418028f,
            "Avenida Cruzeiro Seixas, 7",
            "2650-504",
            "Amadora",
            mutableListOf(
                "https://ubbo.pt/wp-content/uploads/2021/05/UCI-cinemas-3.jpg",
                "https://www.centralcomics.com/wp-content/uploads/2020/11/UCI-Cinemas-600x364.jpg",
                "https://www.centralcomics.com/wp-content/uploads/2020/08/ubbo-600x450.jpg",
                "https://ubbo.pt/wp-content/uploads/2021/04/Cinema-UCi-1-1.jpg"
            ),
            listOf(
                Rating("limpeza de salas", 9),
                Rating("qualidade de som", 8),
                Rating("conforto", 8),
                Rating("qualidade de imagem", 9)
            ),
            listOf(
                Horario("Monday","14:00", "23:00"),
                Horario("Tuesday","14:00", "23:00"),
                Horario("Wednesday","14:00", "23:00"),
                Horario("Thursday","14:00", "23:00"),
                Horario("Friday","14:00", "23:00"),
                Horario("Saturday","10:30", "23:00"),
                Horario( "Sunday","10:30", "23:00")
            )),
        Cinema(id = 5678,
            name = "Cinema City Alegro Alfragide",
            provider = "Cinema City",
            latitude = 38.727573979597146f,
            longitude = -9.221235684181355f,
            address = "Estrada Nacional 117, 67",
            postcode = "2790-143",
            county = "Amadora",
            photos = mutableListOf(
                "https://www.cinemacity.pt/images/editorials/ALFRAGIDE.png",
                "https://www.cinemacity.pt/images/editorials/ALFRAGIDE_2.png",
                "https://www.cinemacity.pt/images/editorials/ALFRAGIDE_3.png",
                "https://www.cinemacity.pt/images/editorials/ALFRAGIDE_4.png",
                "https://alegro.pt/alegro-setubal/wp-content/uploads/sites/3/2018/06/996x520_Cinema_City_03.jpg",
                "https://www.cinemacity.pt/images/editorials/alf_site_square_2.jpg",
                "https://www.distribuicaohoje.com/wp-content/uploads/sites/2/2016/07/Alegro-Alfragide-Distribuição-Hoje.jpg"
            ),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 9),
                Rating(category = "qualidade de som", score = 8),
                Rating(category = "conforto", score = 9),
                Rating(category = "qualidade de imagem", score = 8)
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "13:00", closeHour = "23:00"),
                Horario(dia = "Tuesday", openHour = "13:00", closeHour = "23:00"),
                Horario(dia = "Wednesday", openHour = "13:00", closeHour = "23:00"),
                Horario(dia = "Thursday", openHour = "13:00", closeHour = "23:00"),
                Horario(dia = "Friday", openHour = "13:00", closeHour = "23:30"),
                Horario(dia = "Saturday", openHour = "11:00", closeHour = "23:30"),
                Horario(dia = "Sunday", openHour = "11:00", closeHour = "23:00")
            )),
        Cinema( id = 9876,
            name = "Cinema City Almada Forum",
            provider = "Cinemas NOS",
            latitude = 38.6599766296079f,
            longitude = -9.176672034182891f,
            address = "Centro Comercial Almada Fórum, Loja 3016",
            postcode = "2810-354",
            county = "Almada",
            photos = mutableListOf("https://www.nos.pt/institucional/PT/sobre-a-nos/empresas-e-negocios/PublishingImages/Logos/cinemas.jpg"),
            ratings = listOf(
                Rating(category = "limpeza de salas", score = 9),
                Rating(category = "qualidade de som", score = 8),
                Rating(category = "conforto", score = 9),
                Rating(category = "qualidade de imagem", score = 9),
            ),
            hours = listOf(
                Horario(dia = "Monday", openHour = "14:00", closeHour = "23:00"),
                Horario(dia = "Tuesday", openHour = "14:00", closeHour = "23:00"),
                Horario(dia = "Wednesday", openHour = "14:00", closeHour = "23:00"),
                Horario(dia = "Thursday", openHour = "14:00", closeHour = "23:00"),
                Horario(dia = "Friday", openHour = "14:00", closeHour = "00:00"),
                Horario(dia = "Saturday", openHour = "11:00", closeHour = "00:00"),
                Horario(dia = "Sunday", openHour = "11:00", closeHour = "23:00"),
            ))
    )

    fun pegarCinema(nome : String) : Cinema?{
        val nomeCinema = nome.split(" - ")[0]
        for (cinema in cinemas){
            if(cinema.name == nomeCinema){
                return cinema
            }
        }
        return null
    }

    fun procurarCinema(nome: String) : Boolean{

        val nomeCinema = nome.split(" - ")[0]
        for (cinema in cinemas){
            if(cinema.name == nomeCinema){
                return true
            }
        }
        return false
    }

    val cinemas get() = _cinemas.toList()
}