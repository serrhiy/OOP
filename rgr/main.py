from config import Config
from utils import create_window

config = Config.from_file('config.json')

(root, canvas) = create_window(config)
root.mainloop()
