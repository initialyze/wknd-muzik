// Form Options

(function() {
    "use strict";

    var selectors = {
        self:      '[data-cmp-is="custom-select"]',
    };

    function customSelectHandle (event) {
        event.stopPropagation();
        const customSelect = event.currentTarget;
        if(customSelect.classList.contains('show')) {
            customSelect.classList.remove('show');
        }else {
            customSelect.classList.add('show');
        }
    }

    function closeOnWindowClick (customSelect) {
        document.addEventListener('click', function(event){
            if(event.target !== customSelect){
                if(customSelect.classList.contains('show')) {
                    customSelect.classList.remove('show');
                }
            }
        });
    }
    function customOptionClickHandle (event){
        event.stopPropagation();
        const customSelect = event.currentTarget.parentElement.parentElement;
        const htmlSelect = customSelect.previousElementSibling;
        const val = event.currentTarget.innerText;
        var opts = htmlSelect.options;
        for (var opt, j = 0; opt = opts[j]; j++) {
          if (opt.value == val) {
            htmlSelect.selectedIndex = j;
            customSelect.querySelector('.cmp-custom-select__selected').innerText = opt.innerText;
            customSelect.classList.remove('show');
            break;
          }
        }
    }

    function createCustomSelect(config) {
        const htmlSelect = config.element;
        const htmlOptions = htmlSelect.options;
        // Parent Element of Custom Select
        const customSelect = document.createElement('div');
        customSelect.setAttribute('class','cmp-custom-select');
        // Custom Select Options wrapper
        const customOptions = document.createElement('div');
        customOptions.setAttribute('class','cmp-custom-select__options');
        // Custom Select Selected Option
        const activeOption = document.createElement('div');
        activeOption.setAttribute('class', 'cmp-custom-select__selected');

        customSelect.appendChild(activeOption);
        customSelect.appendChild(customOptions);
        for (var i = 0; i < htmlSelect.options.length; i++) {
            // Custom Select Option
            const customOption = document.createElement('div');
            customOption.setAttribute('class', 'cmp-custom-select__option');
            customOption.innerText = htmlOptions[i].innerText;
            if(i === 0) {
                activeOption.innerText = htmlOptions[i].innerText;
            }else {
                customOption.addEventListener('click', customOptionClickHandle); 
            }
            customOptions.appendChild(customOption);
        }
        htmlSelect.parentElement.appendChild(customSelect);
        customSelect.addEventListener('click', customSelectHandle);
        closeOnWindowClick(customSelect);
    }

    function onDocumentReady() {
        console.log("initializing Custom Select");
        var elements = document.querySelectorAll(selectors.self);
        for (var i = 0; i < elements.length; i++) {
            new createCustomSelect({ element: elements[i] });
        }
    }

    if (document.readyState !== "loading") {
        onDocumentReady();
    } else {
        document.addEventListener("DOMContentLoaded", onDocumentReady);
    }

}());
