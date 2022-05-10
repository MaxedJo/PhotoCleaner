# Интеграция в систему
## Linux
- В каталог, в котором размещен jar файл скопировать файл photoclean.sh
- Дать права на запуск (например: ```chmod +x photoclean.sh```)
- Создать символическую ссылку на него 

```BASH
sudo ln -s <путь_к_скрипту>/photoclean.sh /usr/bin/photoclean
```
### Подключение в контекстное меню Dolphin

файл dolphin/photoclean.desktop  скопировать в ~/.local/share/kservices5/ServiceMenus
В контекстном меню каталогов появится пункт "Photo Cleaner"

### Подключение в контекстное меню Nautilus

файл nautilus/photoclean.sh  скопировать в ~/.local/share/nautilus/scripts и дать права на запуск


