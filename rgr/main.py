import tkinter as tk
from config import Config
from editor import Editor
from menubar import MenuBar
from menus import get_menus
from toolbar import ToolBar
from events.EventEmitter import EventEmitter

config = Config.from_file('config.json')

tools = {
  'brush': ('images/brush.png', lambda: print('brush')),
  'ellipse': ('images/ellipse.png', lambda: print('ellipse')),
  'line': ('images/line.png', lambda: print('line')),
  'rectangle': ('images/rectangle.png', lambda: print('rectangle')),
}

def main(root: tk.Tk, canvas: tk.Canvas, toolbar: ToolBar):
  toolbar.on('shape', print)
  # canvas.create_line(0, 0, canvas.winfo_width(), canvas.winfo_height())

if __name__ == '__main__':
  root = Editor(config)
  menubar = MenuBar(root, get_menus(root))
  toolbarMenu = ToolBar(root, tools)
  canvas = tk.Canvas(root, config.canvas.to_dict())
  root.config(menu=menubar)
  toolbarMenu.pack()
  canvas.pack(fill=tk.BOTH, expand=True)
  def on_configure(_):
    canvas.unbind('<Configure>')
    main(root, canvas, toolbarMenu)
  canvas.bind('<Configure>', on_configure)
  root.mainloop()
