from config import Config
from editor import Editor
from menubar import MenuBar
from menus import get_menus
from toolbar import ToolBar

config = Config.from_file('config.json')

tools = {
  'images/brush.png': lambda: print('brush'),
  'images/ellipse.png': lambda: print('ellipse'),
  'images/line.png': lambda: print('line'),
  'images/rectangle.png': lambda: print('rectangle'),
}

root = Editor(config)
menubar = MenuBar(root, get_menus(root))
toolbarMenu = ToolBar(root, tools)
root.config(menu=menubar)
toolbarMenu.pack()
root.mainloop()
