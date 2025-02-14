package org.api.utils;

public class StatusResponse {
    public static String status200(String operation, String ra) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"token\":\"%s\"}",
                200, ra
        );
        return responseJSON;
    }

    public static String status200Loggout() {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\"}",
                200
        );
        return responseJSON;
    }

    public static String status201(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                201, operacao, "Cadastro realizado com Sucesso"
        );
        return responseJSON;
    }

    public static String status201Update(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                201, operacao, "Edição realizada com sucesso."
        );
        return responseJSON;
    }

    public static String internalError(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                500, operacao, "Error interno, falha ao cadastrar a categoria"
        );
        return responseJSON;
    }

    public static String tableNotExist(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                404, operacao, "A tabela da qual esta tentando inserir dados não existe."
        );
        return responseJSON;
    }

    public static String status201CategoryCreate(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                201, operacao, "Categoria criada com sucesso."
        );
        return responseJSON;
    }

    public static String status201CategoryUpdate(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                201, operacao, "Categoria atualizada com sucesso."
        );
        return responseJSON;
    }

    public static String unauthorized(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                401, operacao, "Acesso não autorizado."
        );
        return responseJSON;
    }

    public static String notFoundOperation(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                401, operacao, "Operação desconhecida."
        );
        return responseJSON;
    }

    public static String status201Delete(String operacao) {
        String responseJSON = "";
        responseJSON = String.format(
                "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                201, operacao, "Deleção realizada com sucesso."
        );
        return responseJSON;
    }

    public static String status401(String operation, String response) {
        String responseJSON = "";
        switch (response.toLowerCase()) {
            case "jsonread":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível ler o json recebido."
                );
                break;
            case "jsonconvert":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível converter os dados para JSON.."
                );
                break;
            case "invalid":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Os campos recebidos não são válidos."
                );
                break;
            case "connectionbd":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "O servidor nao conseguiu conectar com o banco de dados"
                );
                break;
            case "credential":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Credenciais incorretas"
                );
                break;
            case "usernotfound":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "O usuário não existe"
                );
                break;
            case "categorynotfound":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "A categoria não existe"
                );
                break;
            case "nodata":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Vazio"
                );
                break;
            case "userexist":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível cadastrar, pois o usuário informado já existe"
                );
                break;
            case "categoryexist":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível cadastrar, pois a categoria informada já existe"
                );
                break;
            case "categorynotexist":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível atualizar, pois a categoria informada não existe"
                );
                break;
            case "islogged":
                responseJSON = String.format(
                        "{\"status\":\"%d\",\"operacao\":\"%s\",\"mensagem\":\"%s\"}",
                        401, operation, "Não foi possível realizar o login, pois o usuário informado já esta logado"
                );
                break;
        }
        return responseJSON;
    }

}
