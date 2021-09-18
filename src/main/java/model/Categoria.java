package model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
	@Id
	private int idcategoria;
	private String descripcion;
}
