import tkinter as tk
from collections.abc import Callable

type MenuHierarchy = dict[str, Callable[[], None] | MenuHierarchy]

class MenuBar(tk.Menu):

  def __init__(self, root: tk.Tk, menus: MenuHierarchy):
    super().__init__(root)
    self.build_menus(menus, self)

  def build_menus(self, menus: MenuHierarchy, parent: tk.Menu):
    for name, callback in menus.items():
      if isinstance(callback, dict):
        submenu = tk.Menu(parent, tearoff=False)
        self.build_menus(callback, submenu)
        parent.add_cascade(label=name, menu=submenu)
        continue
      parent.add_command(label=name, command=callback)

