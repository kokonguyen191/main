package seedu.recipe.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.recipe.testutil.EventsUtil.postNow;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.testutil.TypicalRecipes.NOURL;
import static seedu.recipe.ui.BrowserPanel.DEFAULT_PAGE_DARK;
import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.recipe.MainApp;
import seedu.recipe.commons.events.ui.RecipePanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private static final String EMPTY_STRING = "";
    private static final String HTML_TAG_REGEX = "\\<.*?>";

    private RecipePanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new RecipePanelSelectionChangedEvent(new RecipeCard(ALICE, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel(true));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE_DARK);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a recipe
        postNow(selectionChangedEventStub);
        URL expectedRecipeUrl = new URL(ALICE.getUrl().toString());

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedRecipeUrl, browserPanelHandle.getLoadedUrl());


        selectionChangedEventStub = new RecipePanelSelectionChangedEvent(new RecipeCard(NOURL, 1));

        // associated web page of a recipe
        postNow(selectionChangedEventStub);
        String expectedHtmlContent = NOURL.getHtmlFormattedRecipe().replaceAll(HTML_TAG_REGEX, EMPTY_STRING);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedHtmlContent, browserPanelHandle.getLoadedHtml());

    }
}
