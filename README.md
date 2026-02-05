[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/3TAzxFga)

Aplicação Móvel CineView

Grupo: Inês Marques 22001936; Robert Cachapa

SCREENSHOTS

Home

![home](https://user-images.githubusercontent.com/79331525/234125498-e83aa53d-9c8a-466a-adcb-31ad4e787f37.png)

PopUp Microfone

![popup](https://user-images.githubusercontent.com/79331525/234123997-3d0b32ce-154e-4c09-8122-a9686a5f766f.png)

Registar Filme

![registarfilme](https://user-images.githubusercontent.com/79331525/234124031-fd78ac46-2fef-4b73-9962-e5758b22efac.png)

Filmes Vistos

![filmesvistos](https://user-images.githubusercontent.com/79331525/234125628-5aa62e4f-2e24-4423-ace7-3f0ea0954b13.png)

Filmes Vistos (Land)

![filmesvistosland](https://user-images.githubusercontent.com/79331525/234126290-322a58f0-4951-4f7b-a04c-44250a49c95d.png)

Filmes Vistos (Mapa)

![mapa](https://user-images.githubusercontent.com/79331525/234125940-e360bb3a-f36e-4967-a2aa-a609fcb0530b.png)

Detalhe do Filme

![detalhe1](https://user-images.githubusercontent.com/79331525/234126347-88b8ba59-9dc3-46c1-812e-f08c70593eae.png)
![detalhe2](https://user-images.githubusercontent.com/79331525/234126435-a6281519-96fe-4695-bbcf-8a33a4448d17.png)

Pesquisa por Atores

![pesquisa](https://user-images.githubusercontent.com/79331525/234126658-c3fee93f-02f6-40ac-982d-cded005581f9.png)

Filmes: [Spider-Man, 2012, Cars, The Maze Runner,  Elysium, Interstellar]

Atores: [Tobey Maguire, Kirsten Dunst, Willem Dafoe, John Cusack, Thandiwe Newton, Chiwetel Ejiofor, Owen Wilson, Bonnie Hunt, Paul Newman, Dylan O'Brien, Kaya Scodelario, Will Poulter, Matt Damon, Jodie Foster, Sharlto Copley, Matthew McConaughey, Anne Hathaway]

FUNCIONALIDADES

![funcionalidades](https://user-images.githubusercontent.com/79331525/234123305-84da9a88-5678-4be7-add4-8cca695b3d2f.png)

Nota esperada: 17

Classes de lógica de negócio:

Classe Cinema:

	- Atributos:
		* id - Int,
		* name - String,	
		* provider - String,	
		* latitude - Float,	
		* longitude - Float,	
		* address - String,	
		* postcode - String,	
		* county - String,	
		* photos - MutableList<String>,	
		* ratings - List<Rating>,	
		* hours - List<Horario>,

	-  Métodos:
		* toString() - String,

Classe Filme:

	- Atributos:
		* nome - String,
		* cartaz - Int,	
		* genero - String,	
		* sinopse - String,	
		* atores - String,	
		* dataLancamento - String,	
		* avaliacaoIMDB - Double,	
		* votosIMBD - Int,	
		* linkIMDB - String,	
		* uuid - String,

	-  Métodos:
		* toString() - String,

Classe Horario:

	- Atributos:
		* dia - String,
		* openHour - String,	
		* closeHour - String,

Classe Rating:

	- Atributos:
		* category - String,
		* score - Int,

Classe RegistoFilme:

	- Atributos:
		* filme - Filme,
		* cinema - Cinema,	
		* rating - Int,	
		* data - String,	
		* photos - MutableList<Bitmap>,	
		* observacoes - String,	
		* uuid - String,

Classe Cinemas:

	- Atributos:
		* _cinemas - mutableListOf<Cinema>,

	-  Métodos:
		* pegarCinema(nome : String) : Cinema?,
		* procurarCinema(nome: String) : Boolean,
		* get() : List<Cinema>,

Classe Filmes:

	- Atributos:
		* _filmes - mutableListOf<Filme>,

	-  Métodos:
		* procurarFilme(nome : String) : Boolean,
		* pegarAtores(): List<String>,
		* procurarAtor(nome:String):Boolean,
		* pegarFilmesAtor(nome:String) : List<Filme>,
		* pegarFilme(nome : String) : Filme?,
		* pegarMaisVistos() : List<Filme>,
		* get() : List<Filme>,

Classe RegistoFilmes:

	- Atributos:
		* _registo_filmes - mutableListOf<RegistoFilme>,

	-  Métodos:
		* submit(filme: Filme, cinema: Cinema, rating: Int, data: String, photoList:MutableList<Bitmap> , observacoes: String),
		* getFilmeById(uuid: String): RegistoFilme,
		* getLasts(): List<RegistoFilme>,
		* get() : List<RegistoFilme>,


FONTES

Lista horizontal de estrelas: chatgpt com a prompt "Como substituo um número do rating para uma lista na horizontal de estrelas"

Adicionar fotos: chatgpt com a prompt "Como faço a aplicacao (Android) aceder à câmera e ser possível adicionar um conjunto de fotos, em Kotlin?"

Dialog Microfone: https://developer.android.com/develop/ui/views/components/dialogs

Countdown Microfone: https://stackoverflow.com/questions/10032003/how-to-make-a-countdown-timer-in-android

AutoComplete para Filmes, Cinemas e Atores: https://stackoverflow.com/questions/39431378/textinputlayout-and-autocompletetextview

Custom Text Fields: https://www.youtube.com/watch?v=IxhIa3eZxz8

Custom Rating Bar: https://www.youtube.com/watch?v=HRIDAFJ4jMs || https://www.youtube.com/watch?v=Dvz2MLJX-Ss&t=195s


