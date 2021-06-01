package br.com.cadastro.alunos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alunos")
public class AlunosController {

    private final List<Alunos> alunos;

    public AlunosController() {
        this.alunos = new ArrayList<>();
        alunos.add(new Alunos(1, "Rômulo", 31));
        alunos.add(new Alunos(2, "Carolina", 27));
        alunos.add(new Alunos(3, "Fernanda", 8));
        alunos.add(new Alunos(4, "Ruan", 18));
    }

    @GetMapping
    public List<Alunos> findAll(@RequestParam(required = false) String nome, Integer idade) {
        if (nome != null) {
            return alunos.stream()
                    .filter(alunoStream -> alunoStream.getNome().contains(nome))
                    .collect(Collectors.toList());
        }
        if (idade != null) {
            return alunos.stream()
                    .filter(alunoStream -> alunoStream.getIdade().equals(idade))
                    .collect(Collectors.toList());
        }
        return alunos;
    }

    @GetMapping("/{id}")
    public Alunos findById(@PathVariable("id") Integer id) {
        return alunos.stream()
                .filter(alunoStream -> alunoStream.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public ResponseEntity<Integer> add(@RequestBody final Alunos nome) {
        if (nome.getId() == null) {
            nome.setId(alunos.size() + 1);
        }
        alunos.add(nome);
        return new ResponseEntity<>(nome.getId(), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody final Alunos nome) {
        alunos.stream()
                .filter(alunoStream -> alunoStream.getId().equals(nome.getId()))
                .forEach(alunoStream -> alunoStream.setNome(nome.getNome()));
        alunos.stream()
                .filter(alunoStream -> alunoStream.getId().equals(nome.getId()))
                .forEach(alunoStream -> alunoStream.setIdade(nome.getIdade()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        alunos.removeIf(alunoStream -> alunoStream.getId().equals(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


