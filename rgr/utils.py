import tkinter as tk

def create_window(config):
  window = config.window
  root = tk.Tk()
  root.geometry('%dx%d' % (window.width, window.height))
  root.title(window.title)
  return root