package frame.base.com.homepage.events;


import frame.base.com.publicevent.BaseEvent;

/**
 * Created by hantao on 2018/2/7.
 */
public class HomePageEvents extends BaseEvent {
    private final boolean isSuccess;
    private final String message;

    private final boolean isLoadmore;
    public HomePageEvents(boolean isLoadmore,boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.isLoadmore=isLoadmore;
    }

    public static HomePageEvents pullSuccess(boolean isLoadmore,boolean isSuccess,String message) {
        return new HomePageEvents(isLoadmore,isSuccess, message);
    }

    public static HomePageEvents pullFale(boolean isLoadmore,String message) {
        return new HomePageEvents(isLoadmore,false, message);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public boolean isLoadmore() {
        return isLoadmore;
    }
}
