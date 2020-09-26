package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id="add-note")
    private WebElement addNoteButton;

    @FindBy(id="nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id="note-title")
    private WebElement noteTitle;

    @FindBy(id="note-description")
    private WebElement noteContent;

    @FindBy(id="noteSubmit")
    private WebElement submitButton;

    @FindBy(id="delete-button")
    private WebElement deleteButton;

    public HomePage(WebDriver driver) { PageFactory.initElements(driver, this); }

    public void createNewNote(String title, String content) {
        this.noteTab.click();
        this.addNoteButton.click();
        this.noteTitle.sendKeys(title);
        this.noteContent.sendKeys(content);
        this.submitButton.click();
    }

    public void deleteNote() {
        this.deleteButton.click();
    }

    public Note getNote() {
        Note note = new Note();
        note.setNoteTitle(noteTitle.getText());
        note.setNoteDescription(noteContent.getText());
        return note;
    }
}
