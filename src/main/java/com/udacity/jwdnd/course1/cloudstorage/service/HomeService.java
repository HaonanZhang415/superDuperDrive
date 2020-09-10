package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HomeService {

    private NoteMapper noteMapper;
    private CredentialMapper credentialMapper;

    public HomeService(NoteMapper noteMapper, CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
        this.noteMapper = noteMapper;
    }

    public void addNote(NoteForm noteForm) {
        Note newNote = new Note();
        newNote.setUserId(noteForm.getUserId());
        newNote.setNoteTitle(noteForm.getNoteTitle());
        newNote.setNoteDescription(noteForm.getNoteDescription());
        noteMapper.addNote(newNote);
    }

    public List<Note> getNotes() {
        List<Note> allNotes = noteMapper.getAllNotes();
        Collections.reverse(allNotes);
        return allNotes;
    }

    public void deleteNote(int noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(NoteForm noteForm, int editId) {
        Note thatNote = new Note();
        thatNote.setNoteId(editId);
        thatNote.setNoteTitle(noteForm.getNoteTitle());
        thatNote.setNoteDescription(noteForm.getNoteDescription());
        noteMapper.updateNote(thatNote);
    }

    public void addCredential(CredentialForm credentialForm) {
        Credential newCredential = new Credential();
        newCredential.setUserId(credentialForm.getUserId());
        newCredential.setUrl(credentialForm.getUrl());
        newCredential.setUsername(credentialForm.getUsername());
        newCredential.setPassword(credentialForm.getPassword());
        credentialMapper.addCredential(newCredential);
    }

    public List<Credential> getCredentials() {
        List<Credential> allCredentials = credentialMapper.getAllCredentials();
        Collections.reverse(allCredentials);
        return allCredentials;
    }

    public void deleteCredential(int credentialId) { credentialMapper.deleteCredential(credentialId); }

    public void updateCredential(CredentialForm credentialForm, int credentialId) {
        Credential thatCredential = new Credential();
        thatCredential.setCredentialId(credentialId);
        thatCredential.setUrl(credentialForm.getUrl());
        thatCredential.setUsername(credentialForm.getUsername());
        thatCredential.setPassword(credentialForm.getPassword());
        credentialMapper.updateCredential(thatCredential);
    }
}
