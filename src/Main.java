import eduni.simjava.Sim_entity;
import eduni.simjava.Sim_system;


public class Main {

    @SuppressWarnings("unused")

    public static void main(String[] args) {

        Sim_system.initialise();

        FichaDeAtendimento fichaDeAtendimento = new FichaDeAtendimento("FichaDeAtendimento", 3);

        BalcaoDeAtendimento balcaoDeAtendimento = new BalcaoDeAtendimento("BalcaoDeAtendimento", 3, 1);

        Triagem triagem = new Triagem("Triagem", 3, 1);

        ClinicoGeral clinicoGeral = new ClinicoGeral("ClinicoGeral", 3, 1);

        Otorrinolaringologista otorrinolaringologista = new Otorrinolaringologista("Otorrinolaringologista", 3, 1);

        Cardiologista cardiologista = new Cardiologista("Cardiologista", 3, 1);

        PostoDeEnfermagem postoDeEnfermagem = new PostoDeEnfermagem("PostoDeEnfermagem", 10, 5);

        AlaDeMedicacao alaDeMedicacao = new AlaDeMedicacao("AlaDeMedicacao", 5);

        //Configuração das portas (Entidade de Origem, Porta de Origem, Entidade de Destino, PortaDestino)

        Sim_system.link_ports("FichaDeAtendimento", "BalcaoDeAtendimento", "BalcaoDeAtendimento", "FichaDeAtendimento");

        Sim_system.link_ports("BalcaoDeAtendimento", "Triagem", "Triagem", "BalcaoDeAtendimento");

        Sim_system.link_ports("Triagem", "ClinicoGeral", "ClinicoGeral", "Triagem");
        Sim_system.link_ports("Triagem", "Otorrinolaringologista", "Otorrinolaringologista", "Triagem");
        Sim_system.link_ports("Triagem", "Cardiologista", "Cardiologista", "Triagem");

        Sim_system.link_ports("ClinicoGeral", "PostoDeEnfermagem", "PostoDeEnfermagem", "ClinicoGeral");
        Sim_system.link_ports("Otorrinolaringologista", "PostoDeEnfermagem", "PostoDeEnfermagem", "Otorrinolaringologista");
        Sim_system.link_ports("Cardiologista", "PostoDeEnfermagem", "PostoDeEnfermagem", "Cardiologista");

        Sim_system.link_ports("PostoDeEnfermagem", "AlaDeMedicacao", "AlaDeMedicacao", "PostoDeEnfermagem");

        //Configura o rastreio para o simulador (default, entity, event)
        Sim_system.set_trace_detail(false, true, false);

        Sim_system.run();
    }

}
