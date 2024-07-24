package LibrerianUI;

public class Application {
    private MainWindow mainFrame;
    private static Application applicationInstance = null;
    private Application(){
        mainFrame = new MainWindow();
        mainFrame.setVisible(true);
    }

    public static Application getInstance() {
        if(Application.applicationInstance == null) {
            Application.applicationInstance = new Application();
        }
        return applicationInstance;
    }

    public MainWindow getMainFrame(){
        return this.mainFrame;
    }

}
