package com.my.controllers;

import com.my.models.Bookmark;
import com.my.repo.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookmarksController {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @GetMapping("/bookmarks")
    public String bookmarksMain(Model model) {
        Iterable<Bookmark> bookmarks = bookmarkRepository.findAll();
        model.addAttribute("bookmarks", bookmarks);
        return "bookmarks/bookmarks-main";
    }

    @GetMapping("/bookmarks/add")
    public String bookmarksAdd() {
        return "bookmarks/bookmarks-add";
    }

    @PostMapping("/bookmarks/add")
    public String bookmarksAdd(@RequestParam String anons,
                               @RequestParam String site) {
        Bookmark bookmark = new Bookmark(anons, site);
        bookmarkRepository.save(bookmark);
        return "redirect:/bookmarks";
    }

    @GetMapping("bookmarks/{id}/edit")
    public String bookmarksEdit(@PathVariable(value = "id") long id, Model model) {
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow();
        model.addAttribute("bookmark", bookmark);
        return "bookmarks/bookmarks-edit";
    }

    @PostMapping("bookmarks/{id}/edit")
    public String bookmarkEdit(@PathVariable(value = "id") long id,
                               @RequestParam String anons,
                               @RequestParam String site) {
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow();
        bookmark.setAnons(anons);
        bookmark.setSite(site);
        bookmarkRepository.save(bookmark);
        return "redirect:/bookmarks";
    }

    @GetMapping("bookmarks/{id}/delete")
    public String bookmarkDelete(@PathVariable(value="id")long id){
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow();
        bookmarkRepository.delete(bookmark);
        return "redirect:/bookmarks";
    }
}
