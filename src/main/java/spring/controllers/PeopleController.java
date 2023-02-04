package spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.dao.PersonDao;
import spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;

    @Autowired
    public PeopleController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping
    public String show (Model model){
        model.addAttribute("people", personDao.showAll());
        return "people/show";
    }

    @GetMapping("/{id}")
    public String index (@PathVariable("id") int id,Model model ){
        model.addAttribute("person", personDao.index(id));
        model.addAttribute("books", personDao.getBooksByPersonId(id));

        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson (@ModelAttribute("person")Person person){
    return "people/new";
    }

    @PostMapping
    public String create (@ModelAttribute("person") @Valid Person person,
                          BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/new";
        }

    personDao.save(person);
    return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
    model.addAttribute("person", personDao.index(id));
    return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @ Valid Person person,
                         BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }

}
