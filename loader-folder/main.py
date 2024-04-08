from pathlib import Path

parentPath = str(Path(__file__).parent.resolve())
parent = parentPath.split("/")[-1]

print(f"Hello from {parent}")
