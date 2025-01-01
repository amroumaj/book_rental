package main

import (
	"database/sql"
	"log"
	"net/http"
	"os"

	"github.com/google/uuid"
	"github.com/gorilla/mux"
	_ "github.com/lib/pq"
)

type Books struct {
	ID          uuid.UUID `json:"id" db:"id"`
	Title       string    `json:"title" db:"title"`
	Author      string    `json:"author" db:"author"`
	Genre       string    `json:"genre" db:"genre"`
	Availabilty bool      `json:"availability" db:"availability"`
}

func main() {
	db, err := sql.Open("postgres", os.Getenv("DB_URL"))

	if err != nil {
		log.Fatal(err)
	}

	defer db.Close()

	router := mux.NewRouter()

	router.HandleFunc("/books", getBooks(db)).Methods("GET")
	router.HandleFunc("/books/{id}", getBook(db)).Methods("GET")

	router.HandleFunc("/books", addBook(db)).Methods("POST")

	router.HandleFunc("/books/{id}", modifyAvailability(db)).Methods("PUT")

	router.HandleFunc("/books/{id}", deleteBook(db)).Methods("DELETE")

	log.Fatal(http.ListenAndServe(":3001", nil))
}

func getBooks(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		rows, err := db.Query("SELECT * FROM books")
		if err != nil {
			log.Fatal(err)
		}
		defer rows.Close()

		books := []Books{}
		for rows.Next() {
			var b Books
			if err := rows.Scan(&b.ID, &b.Title, &b.Author, &b.Genre, &b.Availabilty); err != nil {
				log.Fatal(err)
			}
			books = append(books, b)
		}

		if err := rows.Err(); err != nil {
			log.Fatal(err)
		}
	}
}

func getBook(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		vars := mux.Vars(r)
		id := vars["id"]

		var b Books
		err := db.QueryRow("SELECT * FROM books WHERE id = $1", id).Scan(&b.ID, &b.Title, &b.Author, &b.Genre, &b.Availabilty)

		if err != nil {
			w.WriteHeader(http.StatusNotFound)
			return
		}
	}
}

func addBook(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var b Books

		err := db.QueryRow("INSERT INTO books (title, author, genre, availability) VALUES ($1, $2, $3, $4)", b.Title, b.Author, b.Genre, b.Availabilty).Scan(&b.ID)

		if err != nil {
			log.Fatal(err)
		}
	}
}

func modifyAvailability(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		var b Books

		vars := mux.Vars(r)
		id := vars["id"]

		_, err := db.Exec("UPDATE books SET availability = $1", b.Availabilty, id)

		if err != nil {
			log.Fatal(err)
		}
	}
}

func deleteBook(db *sql.DB) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		vars := mux.Vars(r)
		id := vars["id"]

		var b Books
		err := db.QueryRow("SELECT * FROM books Where id = $1", id).Scan(&b.ID, &b.Title, &b.Author, &b.Genre, &b.Availabilty)

		if err != nil {
			w.WriteHeader(http.StatusNotFound)
			return
		} else {
			_, err := db.Exec("DELETE FROM books where id = $1", id)

			if err != nil {
				w.WriteHeader(http.StatusNotFound)
			}
		}
	}

}
