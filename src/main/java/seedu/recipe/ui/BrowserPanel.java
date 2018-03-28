package seedu.recipe.ui;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.recipe.MainApp;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;
import seedu.recipe.model.recipe.Recipe;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "https://se-edu.github.io/addressbook-level4/DummySearchPage.html?name=";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadRecipePage(Recipe recipe) {
        loadPage(recipe.getUrl().toString());
    }

    //@@author RyanAngJY
    /**
     * Loads the text recipe onto the browser
     */
    private void loadLocalRecipe(Recipe recipe) {
        URL defaultPage2 = MainApp.class.getResource(FXML_FILE_FOLDER + "MusicPlayer.jpg");
        System.out.println(defaultPage2.toExternalForm());
        String testURL = "file:/Users/administrator/Desktop/NotePad.jpg";
        browser.getEngine().loadContent("<img src='" + testURL + "' width=300px/>" );
        // browser.getEngine().loadContent("<strong>" + recipe.getName().toString() + "</strong>");
    }
    //@@author

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handleRecipePanelSelectionChangedEvent(RecipePanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Recipe recipe = event.getNewSelection().recipe;
        System.out.println(recipe.getUrl());
        if (recipe.getUrl().toString().equals("-")) {
            loadLocalRecipe(recipe);
        } else {
            loadRecipePage(recipe);
        }
    }
}
