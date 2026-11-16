# MoveUP — Android

Gerenciador de exercícios de academia. Ideia do mesmo projeto WEB (HTML, JS) porem em Android

A primeira sincronização precisa de internet. O aplicativo funciona sem servidor, login ou chaves de API. Seu identificador é `com.moveup.simples`, permitindo instalar esta versão ao lado da anterior, sem alterar os dados dela. Cada versão possui seu próprio banco.

## Como usar

- A tela inicial apresenta quatro exercícios: agachamento com barra, rosca direta, supino reto e remada baixa.
- Digite parte do nome para filtrar a lista. A busca ignora maiúsculas/minúsculas.
- Toque no exercício para ver detalhes, editar, compartilhar, abrir um site ou excluir.
- Toque em **Novo exercício** para cadastrar nome, descrição, séries, repetições e uma imagem.
- A descrição e a foto própria são opcionais; há uma imagem padrão.
- Séries: 1 a 50. Repetições: 1 a 500. Nome obrigatório com até 80 caracteres.

## Organização simples

| Pasta | Arquivo | Responsabilidade |
|---|---|---|
| modelo | `Exercicio.kt` | Dados de um exercício e entidade do Room |
| dados | `ExercicioDao.kt` | Consultar, inserir, atualizar e excluir |
| dados | `BancoDados.kt` | Criar o banco e inserir o catálogo uma única vez |
| dados | `CatalogoExercicios.kt` | Exercícios iniciais e textos traduzidos |
| adaptador | `ExercicioAdapter.kt` | Preencher os itens do RecyclerView |
| telas | `MainActivity.kt` | Lista, busca e acesso ao cadastro |
| telas | `DetalhesActivity.kt` | Detalhes, edição, exclusão e Intents implícitas |
| telas | `CadastroActivity.kt` | Formulário compartilhado de inclusão e edição |
| telas | `RecursosTela.kt` | Margens das barras do sistema e exibição de fotos |

Não são usados ViewModel, Repository, StateFlow, injeção de dependência ou fábricas de objetos de tela. As Activities chamam o DAO diretamente. O `ExercicioAdapter` usa a atualização simples com `notifyDataSetChanged()`, adequada ao catálogo pequeno deste trabalho.

## Requisitos atendidos no código

| Requisito | Arquivo(s) |
|---|---|
| RF01 — Lista com imagem e nome | `activity_main.xml`, `item_exercicio.xml`, `ExercicioAdapter.kt` |
| RF01 — Intent explícita para detalhes | `configurarLista()` em `MainActivity.kt` |
| RF02 — Compartilhar e abrir site | `compartilharExercicio()` e `configurarBotoes()` em `DetalhesActivity.kt` |
| RF03 — Incluir e editar | `CadastroActivity.kt`, método `salvarExercicio()` |
| RF03 — Excluir | `confirmarExclusao()` e `excluirExercicio()` em `DetalhesActivity.kt` |
| RF04 — Room | `Exercicio.kt`, `ExercicioDao.kt`, `BancoDados.kt` |
| RF04 — Imagem como URI String | Campo `imagemUri`, seletor `OpenDocument` e permissão persistente |
| RF05 — Inglês e pt-BR | `res/values/strings.xml` e `res/values-pt-rBR/strings.xml` |
| RF06 — Claro e escuro | `colors.xml` e `themes.xml` em `values` e `values-night` |
| RNF01 — Kotlin/ViewBinding/ConstraintLayout | Três Activities, quatro layouts e configuração do módulo app |
| RNF02 — Recursos organizados | Textos e cores referenciados por recursos XML |
| RNF03 — Organização e legibilidade | Pacotes separados e funções pequenas em português |

## Persistência

O banco `moveup_simples.db` é criado pelo Room. O catálogo é inserido no callback `onCreate` do banco, dentro da criação inicial. Ele não é inserido novamente a cada abertura. Assim, apagar todos os exercícios mantém a lista vazia até o usuário cadastrar novos itens.


