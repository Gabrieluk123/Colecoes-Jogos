# Coleção de Jogos 🎮

O **Coleção de Jogos** é um sistema desenvolvido para gerenciar coleções de jogos, permitindo o acompanhamento de estatísticas e organização de informações sobre cada título.

## 🚀 Funcionalidades

- **Gerenciamento de Jogos**:
  - Adicionar novos jogos à coleção.
  - Editar informações dos jogos existentes.
  - Excluir jogos da coleção.
  - Listar e buscar jogos cadastrados.

- **Estatísticas**:
  - Geração de dados estatísticos sobre a coleção.
  - Visualização de insights como número de jogos por gênero ou desenvolvedor.

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Java
- **Framework**: Maven
- **Banco de Dados**: MySQL (configuração especificada no arquivo `BancoDeDados(CODIGO).txt`).
- **IDE**: NetBeans

## 📂 Estrutura do Projeto

### Controladores
- **EstatisticaController**:
  - Responsável pela lógica de geração e exibição de estatísticas relacionadas aos jogos.
  
- **PrincipalController**:
  - Gerencia as operações principais do sistema, incluindo navegação entre telas e integração de funcionalidades.

### Modelos
- **Colecao**:
  - Classe central que representa a coleção de jogos.
  - Contém os métodos e atributos necessários para manipular os jogos cadastrados.

### Outros Arquivos
- **`pom.xml`**:
  - Gerenciamento de dependências e configurações do projeto Maven.
  
- **`BancoDeDados(CODIGO).txt`**:
  - Instruções ou script SQL para configurar o banco de dados.

## 🔧 Configuração e Execução

### Pré-requisitos
- JDK 11+
- Maven
- Banco de Dados (MySQL ou conforme especificado no projeto)
- IDE (como NetBeans ou IntelliJ IDEA)

### Passos para execução
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/colecao-jogos.git
