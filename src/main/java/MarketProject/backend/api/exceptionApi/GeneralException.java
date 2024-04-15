package MarketProject.backend.api.exceptionApi;

public class GeneralException extends  Exception{

    public GeneralException (){
        super("An error occurred while processing your request");
    }
}
