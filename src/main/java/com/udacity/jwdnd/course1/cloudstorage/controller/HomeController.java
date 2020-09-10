package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.service.HomeService;
import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;
    private UserService userService;
    private StorageService storageService;

    public HomeController(HomeService homeService, UserService userService, StorageService storageService) {
        this.userService = userService;
        this.homeService = homeService;
        this.storageService = storageService;
    }

    @GetMapping
    public String getNotePage(NoteForm noteForm, CredentialForm credentialForm, Model model, MultipartFile fileUpload) {
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "addNote")
    public String addNotePage(Authentication authentication, NoteForm noteForm, Model model, CredentialForm credentialForm, MultipartFile fileUpload) {
        noteForm.setUserId(userService.getUser(authentication.getName()).getUserId());
        this.homeService.addNote(noteForm);
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "removeNote")
    public String deleteNotePage(NoteForm noteForm, Model model, HttpServletRequest req, CredentialForm credentialForm, MultipartFile fileUpload) {
        this.homeService.deleteNote(Integer.valueOf(req.getParameter("removeNote")));
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "editNote")
    public String editNotePage(Authentication authentication, NoteForm noteForm, Model model, HttpServletRequest req, CredentialForm credentialForm, MultipartFile fileUpload) {
        noteForm.setUserId(userService.getUser(authentication.getName()).getUserId());
        int editId = Integer.valueOf(req.getParameter("editNote"));
        this.homeService.updateNote(noteForm, editId);
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

//    @GetMapping(params = "getCredential")
//    public String getCredentialPage(CredentialForm credentialForm, Model model) {
//        model.addAttribute("credentials", this.homeService.getCredentials());
//        return "home";
//    }

    @PostMapping(params = "addCredential")
    public String addCredentialPage(Authentication authentication, CredentialForm credentialForm, Model model, NoteForm noteForm, MultipartFile fileUpload) {
        credentialForm.setUserId(userService.getUser(authentication.getName()).getUserId());
        this.homeService.addCredential(credentialForm);
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "removeCredential")
    public String deleteCredentialPage(CredentialForm credentialForm, Model model, HttpServletRequest req, NoteForm noteForm, MultipartFile fileUpload) {
        this.homeService.deleteCredential(Integer.valueOf(req.getParameter("removeCredential")));
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "editCredential")
    public String editCredentialPage(Authentication authentication, CredentialForm credentialForm, Model model, HttpServletRequest req, NoteForm noteForm, MultipartFile fileUpload) {
        credentialForm.setUserId(userService.getUser(authentication.getName()).getUserId());
        int editId = Integer.valueOf(req.getParameter("editCredential"));
        this.homeService.updateCredential(credentialForm, editId);
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("notes", this.homeService.getNotes());
        model.addAttribute("files", this.storageService.getFileName());
        return "home";
    }

    @PostMapping(params = "fileUpload")
    public String handleFileUpload(MultipartFile fileUpload, Model model, Authentication authentication, NoteForm noteForm, CredentialForm credentialForm) {
        Integer userId = userService.getUser(authentication.getName()).getUserId();
        this.storageService.save(fileUpload, userId);
        model.addAttribute("files", this.storageService.getFileName());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("notes", this.homeService.getNotes());
        return "home";
    }

    @PostMapping(params = "fileDownload")
    public ResponseEntity<Resource> handleFileDownload(HttpServletRequest req, Model model, NoteForm noteForm, CredentialForm credentialForm) {
        Resource file = this.storageService.download(Integer.valueOf(req.getParameter("fileDownload")));
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping(params = "fileDelete")
    public String handleFileDelete(HttpServletRequest req, Model model, NoteForm noteForm, CredentialForm credentialForm) {
        this.storageService.delete(Integer.valueOf(req.getParameter("fileDelete")));
        model.addAttribute("files", this.storageService.getFileName());
        model.addAttribute("credentials", this.homeService.getCredentials());
        model.addAttribute("notes", this.homeService.getNotes());
        return "home";
    }
}
