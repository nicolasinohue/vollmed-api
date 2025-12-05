package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String email,
        String telefone,
        String cpf,
        Endereco endereco) {
    public DadosDetalhamentoPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getTelefone(),
                paciente.getCpf(),
                new Endereco(
                        paciente.getEndereco().getLogradouro(),
                        paciente.getEndereco().getNumero(),
                        paciente.getEndereco().getComplemento(),
                        paciente.getEndereco().getBairro(),
                        paciente.getEndereco().getCidade(),
                        paciente.getEndereco().getUf(),
                        paciente.getEndereco().getCep()
                )
        );
    }
}
