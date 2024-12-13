import tkinter as tk

class Editor(tk.Tk):

  def __init__(self, config):
    super().__init__()
    window = config.window
    self.geometry('%dx%d' % (window.width, window.height))
    self.title(window.title)
