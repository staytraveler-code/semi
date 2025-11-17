package userInterface;

public class SleepUtil {
	
	/**
     * 지정된 시간(ms)만큼 현재 스레드를 일시 중지합니다.
     * InterruptedException 발생 시, 인터럽트 상태를 복원하고 로그를 남깁니다.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // InterruptedException이 발생했을 때,
            // 현재 스레드의 인터럽트 상태를 다시 설정(복원)해주는 것이 좋습니다.
            // 이렇게 하면 이 스레드의 상위 호출자가 인터럽트 발생 여부를 알 수 있습니다.
            Thread.currentThread().interrupt(); 
            // 런타임 예외로 감싸서 다시 던지거나, 로그를 남길 수도 있습니다.
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
    
    

}
