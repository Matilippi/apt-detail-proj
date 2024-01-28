package com.detail.app.mydetail.view;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import javax.swing.DefaultListModel;

import com.detail.app.mydetail.controller.SurgeriesController;
import com.detail.app.mydetail.model.Surgery;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(GUITestRunner.class)
public class SurgeryScreenTest extends AssertJSwingJUnitTestCase {
	private FrameFixture window;
	private SurgeryScreen surgeryScreen;
	
	@Mock
	private SurgeriesController surgeriesController;
	private AutoCloseable closeable;
	
	@Override
	protected void onSetUp() {
		closeable = MockitoAnnotations.openMocks(this);
		GuiActionRunner.execute(()-> {
			surgeryScreen = new SurgeryScreen();
			surgeryScreen.setSurgeriesController(surgeriesController);
			return surgeryScreen;
		});
		window = new FrameFixture(robot(), surgeryScreen);
		window.show();
	}
	@Override
	protected void onTearDown() throws Exception{
		closeable.close();
	}
	
	@Test
	public void testControlsInitialState() {
		window.label(JLabelMatcher.withText("id"));
		window.textBox("TextBoxField").requireEnabled();
		window.label(JLabelMatcher.withText("patient Name"));
		window.textBox("PatientNameBoxField").requireEnabled();
		window.list("SurgeriesList");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		window.button(JButtonMatcher.withText("Remove")).requireDisabled();
		window.label(JLabelMatcher.withText(" "));
	}
	
	@Test
	public void testWhenIdAndNameAreNonEmptyThenAddButtonShouldBeEnabled() {
		window.textBox("TextBoxField").enterText("1");
		window.textBox("PatientNameBoxField").enterText("paziente1");
		window.button(JButtonMatcher.withText("Add")).requireEnabled();
	}
	@Test
	public void testWhenIdOrNameAreBlankThenAddButtonShouldBeDisabled() {
		JTextComponentFixture idTextBox = window.textBox("TextBoxField");
		JTextComponentFixture patientNameTextBox = window.textBox("PatientNameBoxField");
		idTextBox.enterText("1");
		patientNameTextBox.enterText(" ");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
		idTextBox.setText("");
		patientNameTextBox.setText("");
		idTextBox.enterText(" ");
		patientNameTextBox.enterText("paziente1");
		window.button(JButtonMatcher.withText("Add")).requireDisabled();
	}
	@Test
	public void testDeleteButtonShouldBeEnabledWhenSurgeryIsSelected() {
		GuiActionRunner.execute(()->
			surgeryScreen.getListSurgeriesModel().addElement(new Surgery("1","paziente1"))
		);
		window.list("SurgeriesList").selectItem(0);
		JButtonFixture deleteButton = window.button(JButtonMatcher.withText("Remove"));
		deleteButton.requireEnabled();
		window.list("SurgeriesList").clearSelection();
		deleteButton.requireDisabled();
	}
	@Test
	public void testShowAllSurgeriesShouldAddSurgeryDescToTheList(){
		Surgery surgery1 = new Surgery("1","paziente1");
		Surgery surgery2 = new Surgery("2","paziente2");
		GuiActionRunner.execute(
				() -> surgeryScreen.showAllSurgeries(
						Arrays.asList(surgery1, surgery2))
			);
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(surgery1.toString(), surgery2.toString());
	}
	@Test
	public void testShowErrorShouldShowTheMessageInTheErrorLabel(){
		Surgery surgery1 = new Surgery("1","paziente1");
		GuiActionRunner.execute(
				() -> surgeryScreen.showError("error message: ", surgery1));
		window.label("error").requireText("error message: " + surgery1);
	}
	@Test
	public void testSurgeryAddedShouldAddTheSurgeryToTheListAndResetTheError(){
		Surgery surgery = new Surgery("1", "paziente1");
		GuiActionRunner.execute(() -> 
		surgeryScreen.surgeryAdded(new Surgery("1", "paziente1")
		));
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(surgery.toString());
		window.label("error").requireText("");
	}
	@Test
	public void testSurgeryRemovedShouldRemoveTheSurgeryToTheListAndResetTheError(){
		Surgery surgery1 = new Surgery("1", "paziente1");
		Surgery surgery2 = new Surgery("2", "paziente2");
		GuiActionRunner.execute(() -> {
			DefaultListModel<Surgery> listSurgeryModel = surgeryScreen.getListSurgeriesModel();
			listSurgeryModel.addElement(surgery1);
			listSurgeryModel.addElement(surgery2);
		});
		GuiActionRunner.execute(() -> {
			surgeryScreen.surgeryRemoved(new Surgery("1", "paziente1"));
		});
		
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(surgery2.toString());
		window.label("error").requireText("");
	}
	@Test
	public void testAddButtonShouldDelegateToSurgeryControllerNewSurgery() {
		window.textBox("TextBoxField").enterText("1");
		window.textBox("PatientNameBoxField").enterText("paziente1");
		window.button(JButtonMatcher.withText("Add")).click();
		verify(surgeriesController).newSurgery(new Surgery("1", "paziente1"));
	}
	/**@Test
	public void testDeleteButtonShouldDelegateToSurgeryControllerDeleteSurgery() {
		Surgery surgery1 = new Surgery("1", "paziente1");
		Surgery surgery2 = new Surgery("2", "paziente2");
		GuiActionRunner.execute(() -> {
			DefaultListModel<Surgery> listSurgeriesModel = surgeryScreen.getListSurgeriesModel();
			listSurgeriesModel.addElement(surgery1);
			listSurgeriesModel.addElement(surgery2);
		});
		window.list("SurgeriesList").selectItem(1);
		window.button(JButtonMatcher.withText("Remove")).click();
		verify(surgeriesController).deleteSurgery(surgery2);
	}**/

}
