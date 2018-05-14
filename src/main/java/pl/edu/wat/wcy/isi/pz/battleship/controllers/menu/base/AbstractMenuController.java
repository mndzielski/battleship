package pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.base.AbstractController;
import pl.edu.wat.wcy.isi.pz.battleship.controllers.menu.MenuController;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractMenuController extends AbstractController {
    protected MenuController menuController;
}
