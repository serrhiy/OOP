from config import Config
from editor import Editor
from menubar import MenuBar
from menus import get_menus

config = Config.from_file('config.json')

root = Editor(config)

menubar = MenuBar(root, get_menus(root))
root.config(menu=menubar)

root.mainloop()
