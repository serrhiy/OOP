import tkinter as tk

def create_window(config):
  window = config.window
  root = tk.Tk()
  root.geometry('%dx%d' % (window.width, window.height))
  root.title(window.title)
  canvas = tk.Canvas(root, config.canvas.to_dict())
  canvas.pack(fill=tk.BOTH, expand=True)
  return (root, canvas)