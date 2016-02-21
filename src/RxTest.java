import rx.Observable;
import rx.Subscriber;

/**
 * Date: 21.02.2016
 * Time: 21:23
 */
public class RxTest {
    public static void main(String[] args) {
        Observable.OnSubscribe<String> subscribeFunction = RxTest::asyncProcessingOnSubscribe;

        Observable asyncObservable = Observable.create(subscribeFunction);

        asyncObservable.skip(5).subscribe(System.out::println);
    }

    private static void asyncProcessingOnSubscribe(Subscriber s) {
        Thread thread = new Thread(() -> produceSomeValues(s));
        thread.start();
    }

    private static void produceSomeValues(Subscriber subscriber) {
        for (int ii = 0; ii < 10; ii++) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.onNext("Pushing value from async thread " + ii + ", thread id: " + Thread.currentThread().getId());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }
}
