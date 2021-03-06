package edu.upeu.school.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.upeu.school.service.DocenteService;
import edu.upeu.school.web.form.DocenteForm;

@Controller
@RequestMapping(value = "/docentes")
public class DocenteController {

	@Autowired
	private DocenteService docenteService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String mostrarListaDocentes(Model model) {

		List<DocenteForm> lista = docenteService.getListaDocentes();
		model.addAttribute("lp", lista);

		return "docente/main";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.GET)
	public String mostrarFormularioDocenteNuevo() {
		return "docente/formulario";
	}

	@RequestMapping(value = "/nuevo", method = RequestMethod.POST)
	public String guardarDocenteNuevo(@ModelAttribute DocenteForm df) {

		docenteService.guardarDocente(df);

		return "redirect:/docentes/";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String mostrarFormularioDocente(@PathVariable String id, Model model) {

		DocenteForm df = docenteService.getDocentePorId(id);
		model.addAttribute("docente", df);

		return "docente/formulario";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String actualizarDocente(@PathVariable String id,
			@ModelAttribute DocenteForm df, Model model) {

		docenteService.guardarDocente(df);

		return "redirect:/docentes/";
	}

	@RequestMapping(value = "/{id}/cursos", method = RequestMethod.GET)
	public String mostrarDocenteCursos(@PathVariable String id, Model model) {

		DocenteForm df = docenteService.getDocenteCursosPorId(id);
		model.addAttribute("docente", df);

		return "docente/docente_cursos";
	}

	@RequestMapping(value = "/{id}/cursos/editar", method = RequestMethod.GET)
	public String mostrarFormularioDocenteCursos(@PathVariable String id,
			Model model) {

		DocenteForm df = docenteService.getDocenteAllCursosPorId(id);
		model.addAttribute("docente", df);

		return "docente/formulario_cursos";
	}

	@RequestMapping(value = "/{id}/cursos/editar", method = RequestMethod.POST)
	public String guardarDocenteCursos(@PathVariable String id,
			@RequestParam(value = "id_curso") String[] ids, Model model) {

		docenteService.guardarCursosDocente(id,ids);

		return "redirect:/docentes/" + id + "/cursos";
	}
}
