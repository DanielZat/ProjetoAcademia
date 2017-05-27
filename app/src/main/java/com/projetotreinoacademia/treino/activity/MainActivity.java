package com.projetotreinoacademia.treino.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;
import com.projetotreinoacademia.R;
import com.projetotreinoacademia.treino.adapter.CadastroTreinoAdapter;
import com.projetotreinoacademia.treino.adapter.NavDrawerMenuAdapter;
import com.projetotreinoacademia.treino.adapter.NavDrawerMenuItem;
import com.projetotreinoacademia.treino.dao.ExercicioDAO;
import com.projetotreinoacademia.treino.dao.TreinoDAO;
import com.projetotreinoacademia.treino.fragments.CadastroExercicioFragment;
import com.projetotreinoacademia.treino.fragments.CadastroTreinoFragment;
import com.projetotreinoacademia.treino.fragments.ListaExerciciosFragment;
import com.projetotreinoacademia.treino.fragments.ListaTreinosFragment;
import com.projetotreinoacademia.treino.fragments.TreinoDiarioFragment;
import com.projetotreinoacademia.treino.model.Exercicio;
import com.projetotreinoacademia.treino.model.Treino;

import java.util.ArrayList;
import java.util.List;
import livroandroid.lib.fragment.NavigationDrawerFragment;
import static livroandroid.lib.fragment.NavigationDrawerFragment.*;

public class MainActivity extends BaseActivity implements NavigationDrawerCallbacks,CadastroExercicioFragment.CadastroExercicioFragmentListener,CadastroTreinoFragment.CadastroTreinoFragmentListener, TreinoDiarioFragment.TreinoDiarioFragmentListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavDrawerMenuAdapter listAdapter;
    ExercicioDAO exercicioDAO = new ExercicioDAO(this);
    TreinoDAO treinoDAO = new TreinoDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();

        //Nav Drawer
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);

        //Configura o drawer layout
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment.setUp(drawerLayout);

        drawerLayout.setStatusBarBackground(R.color.primary_dark);
    }

    @Override
    public NavDrawerListView getNavDrawerView(NavigationDrawerFragment navDrawerFrag, LayoutInflater layoutInflater, ViewGroup container) {
        //Deve retornar a view e o identificador do ListView
        View view = layoutInflater.inflate(R.layout.nav_drawer_listview,container,false);

        //Preenche o cabeçalho com a foto, nome e email
        navDrawerFrag.setHeaderValues(view, R.id.listViewContainer, R.drawable.header_nav_drawer, R.drawable.treine_icon, R.string.app_name, R.string.sub_title_nav);

        return new NavDrawerListView(view,R.id.listView);
    }

    @Override
    public ListAdapter getNavDrawerListAdapter(NavigationDrawerFragment navDrawerFrag) {
        //Este método deve retornar o adapter que vai preencher o ListView
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        //Deixa o primeiro item selecionado
        this.listAdapter = new NavDrawerMenuAdapter(this,list);
        return listAdapter;
    }

    @Override
    public void onNavDrawerItemSelected(NavigationDrawerFragment navDrawerFrag, int position) {
        //Método chamado ao selecionar um item do ListView
        List<NavDrawerMenuItem> list = NavDrawerMenuItem.getList();
        NavDrawerMenuItem selectedItem = list.get(position);
        //Seleciona a linha
        this.listAdapter.setSelected(position, true);

        if(position == 0){
            replaceFragment(new TreinoDiarioFragment());
        }
        else if(position == 1){
            replaceFragment(new CadastroExercicioFragment());
        }
        else if(position == 2){
            replaceFragment(new CadastroTreinoFragment());
        }
        else if(position == 3){
            replaceFragment(new ListaExerciciosFragment());
        }
        else if(position == 4){
            replaceFragment(new ListaTreinosFragment());
        }
        else{
            Toast.makeText(MainActivity.this,"Opção Inválida", Toast.LENGTH_LONG).show();
        }
    }

    //Adiciona o fragment no centro da tela
    private void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_drawer_container,frag,"TAG").commit();
    }

    @Override
    public void inserirExercicio() {

        Exercicio exercicio = new Exercicio();

        EditText textoDescricao = (EditText) findViewById(R.id.txtNomeTreino);
        EditText textoRepeticoes = (EditText) findViewById(R.id.txtRepeticoes);
        EditText textoCarga = (EditText) findViewById(R.id.txtCarga);

        if(textoDescricao.getText().toString().isEmpty() || textoDescricao.getText().toString().isEmpty() || textoCarga.getText().toString().toString().isEmpty()){
            Toast.makeText(MainActivity.this,"Existem campos em branco! Favor preencher",Toast.LENGTH_LONG).show();
        }
        else{
            exercicio.setDescricao(textoDescricao.getText().toString());
            exercicio.setRepeticoes(textoRepeticoes.getText().toString());
            exercicio.setCarga(Integer.parseInt(textoCarga.getText().toString()));

            String retorno = exercicioDAO.inserir(exercicio);

            Toast.makeText(MainActivity.this,retorno,Toast.LENGTH_LONG).show();

            textoDescricao.setText("");
            textoRepeticoes.setText("");
            textoCarga.setText("");
            textoDescricao.requestFocus();
        }
    }

    @Override
    public void inserirTreino(List<Exercicio> exercicios) {

        Treino treino = new Treino();
        List<Exercicio> exerciciosSelecionados = new ArrayList<>();

        EditText textoDescricao = (EditText) findViewById(R.id.txtNomeTreino);

        for(int i = 0; i<exercicios.size(); i++){
            if(exercicios.get(i).isSelected()){
                exerciciosSelecionados.add(exercicios.get(i));
            }
        }

        treino.setNome(textoDescricao.getText().toString());
        treino.setExercicios(exerciciosSelecionados);

        String retorno = treinoDAO.inserir(treino);

        Toast.makeText(MainActivity.this,retorno,Toast.LENGTH_LONG).show();

        textoDescricao.setText("");
        textoDescricao.requestFocus();
    }

    @Override
    public void FinalizarTreino(Treino treino, List<Exercicio> exercicios) {

        boolean exerciciosRealizados = true;

        for(int i = 0; i<exercicios.size(); i++){
            if(exercicios.get(i).isSelected()){
                continue;
            }
            else{
                Toast.makeText(MainActivity.this,"Existem exercícios pendentes",Toast.LENGTH_LONG).show();
                exerciciosRealizados = false;
                break;
            }
        }

        if(exerciciosRealizados){
            String retorno = treinoDAO.finalizarTreino(treino.getId());
            Toast.makeText(MainActivity.this,retorno,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_close){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
