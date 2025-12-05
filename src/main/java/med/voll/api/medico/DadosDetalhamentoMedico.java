package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String email,
        String telefone,
        String crm,
        Especialidade especialidade,
        Endereco endereco
) {
    public DadosDetalhamentoMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNome(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getCrm(),
                medico.getEspecialidade(),
                new Endereco(
                        medico.getEndereco().getLogradouro(),
                        medico.getEndereco().getNumero(),
                        medico.getEndereco().getComplemento(),
                        medico.getEndereco().getBairro(),
                        medico.getEndereco().getCidade(),
                        medico.getEndereco().getUf(),
                        medico.getEndereco().getCep()
                )
        );
    }
}
