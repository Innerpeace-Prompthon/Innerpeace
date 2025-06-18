<<<<<<< HEAD
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import path from "path";

// https://vite.dev/config/
=======
import { defineConfig } from "vite"
import react from "@vitejs/plugin-react"
import path from "path"

>>>>>>> 09b69170f3912044cf962b250774eab209ac6a1b
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
<<<<<<< HEAD
      "@": path.resolve(__dirname, "src"),
    },
  },
});
=======
      "@": path.resolve(__dirname, "./src"),
    },
  },
})
>>>>>>> 09b69170f3912044cf962b250774eab209ac6a1b
