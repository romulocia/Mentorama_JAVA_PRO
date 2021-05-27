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
        alunos.add(new Alunos(3, "Camila", 21));
        alunos.add(new Alunos(4, "Heloisa", 55));
        alunos.add(new Alunos(5, "João", 9));
    }

    @GetMapping
    public List<Alunos> findAll(@RequestParam(required = false) String nome) {
        if (nome != null) {
            return alunos.stream()
                    .filter(nomeFilter -> nomeFilter.getNome().contains(nome))
                    .collect(Collectors.toList());
        }
        return alunos;
    }

    @GetMapping("/{idade}")
    public Alunos findByIdade(@PathVariable("idade") Integer idade) {
        return alunos.stream()
                .filter(idadeFilter -> idadeFilter.getIdade().equals(idade))
                .findFirst()
                .orElse(null);
    }

    @GetMapping("/{id}")
    public Alunos findById(@PathVariable("id") Integer id) {
        return alunos.stream()
                .filter(idFilter -> idFilter.getId().equals(id))
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
                .filter(msg -> msg.getId().equals(nome.getId()))
                .forEach(msg -> msg.setNome(nome.getNome()));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        alunos.removeIf(msg -> msg.getId().equals(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


