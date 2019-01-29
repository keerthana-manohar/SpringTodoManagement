package com.webapp.Todo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class TodoController {

	@Autowired
	private TodoService service;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodosList(ModelMap model) {
		String user = getLoggedInUserName();
		System.out.println("user is!----" + user);
		model.addAttribute("todos", service.retrieveTodos(user));
		return "list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		model.addAttribute("todo", new Todo());
		model.addAttribute("user", getLoggedInUserName());
		System.out.println("user is in /add-todo get " + getLoggedInUserName());
		return "todo";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors())
			return "todo";

		service.addTodo(getLoggedInUserName(), todo.getDesc(), todo.getTargetDate(), false);
		System.out.println("user is---" + getLoggedInUserName());
		// model.clear();// to prevent request parameter "name" to be passed
		return "redirect:/list-todos?user=" + getLoggedInUserName();
	}

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {

			return ((UserDetails) principal).getUsername();
		}
		return null;
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(ModelMap model, @RequestParam int id) {
		model.addAttribute("todo", service.RetrieveTodo(id));
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors())
			return "todo";

		todo.setUser(getLoggedInUserName());
		service.UpdateTodo(todo);

		model.clear();// to prevent request parameter "name" to be passed
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
		service.deleteTodo(id);

		return "redirect:/list-todos";
	}

}

/*
 * package com.webapp.Todo;
 * 
 * import java.text.SimpleDateFormat; import java.util.Date;
 * 
 * import javax.validation.Valid;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.beans.propertyeditors.CustomDateEditor; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.ModelMap; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.WebDataBinder; import
 * org.springframework.web.bind.annotation.InitBinder; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestMethod; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.SessionAttributes;
 * 
 * @Controller
 * 
 * @SessionAttributes("name") public class TodoController {
 * 
 * @Autowired private TodoService service;
 * 
 * @InitBinder protected void initBinder(WebDataBinder binder) {
 * SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
 * binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,
 * false)); }
 * 
 * @RequestMapping(value = "/list-todos", method = RequestMethod.GET) public
 * String showTodosList(ModelMap model) { // String user = (String)
 * model.get("name"); model.addAttribute("todos",
 * service.retrieveTodos(retrievedLoggedInUsername())); return "list-todos"; }
 * 
 * private String retrievedLoggedInUsername() { return "Keerthana"; }
 * 
 * @RequestMapping(value = "/add-todo", method = RequestMethod.GET) public
 * String showAddTodoPage(ModelMap model) { model.addAttribute("todo", new
 * Todo()); return "todo"; }
 * 
 * @RequestMapping(value = "/add-todo", method = RequestMethod.POST) public
 * String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
 * 
 * if (result.hasErrors()) return "todo";
 * 
 * service.addTodo((String) model.get("name"), todo.getDesc(), new Date(),
 * false); model.clear();// to prevent request parameter "name" to be passed
 * return "redirect:/list-todos"; }
 * 
 * @RequestMapping(value = "/update-todo", method = RequestMethod.GET) public
 * String showUpdateTodoPage(ModelMap model, @RequestParam int id) {
 * model.addAttribute("todo", service.RetrieveTodo(id)); return "todo"; }
 * 
 * @RequestMapping(value = "/update-todo", method = RequestMethod.POST) public
 * String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
 * if (result.hasErrors()) return "todo";
 * 
 * todo.setUser(retrievedLoggedInUsername()); // TODO:Remove Hardcoding Later
 * service.UpdateTodo(todo);
 * 
 * model.clear();// to prevent request parameter "name" to be passed return
 * "redirect:/list-todos"; }
 * 
 * @RequestMapping(value = "/delete-todo", method = RequestMethod.GET) public
 * String deleteTodo(@RequestParam int id) { service.deleteTodo(id);
 * 
 * return "redirect:/list-todos"; }
 * 
 * }
 */
