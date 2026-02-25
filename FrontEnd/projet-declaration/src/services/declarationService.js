const API_BASE_URL = 'http://localhost:8080/api/declarations';

export const declarationService = {
    /**
     * Fetch all declarations
     * GET /api/declarations/all
     */
    getAllDeclarations: async () => {
        try {
            const response = await fetch(`${API_BASE_URL}/all`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching all declarations:", error);
            throw error;
        }
    },

    /**
     * Fetch declaration details by ID
     * GET /api/declarations/details
     * Composite ID: debur, deimpexp, derepert
     */
    getDeclarationDetails: async (id) => {
        try {
            const queryParams = new URLSearchParams(id).toString();
            const response = await fetch(`${API_BASE_URL}/details?${queryParams}`);
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching declaration details:", error);
            throw error;
        }
    },

    /**
     * Search declarations with criteria
     * POST /api/declarations/search
     */
    searchDeclarations: async (criteria, page = 0, size = 10, sortBy = 'dedatin', direction = 'DESC') => {
        try {
            const url = `${API_BASE_URL}/search?page=${page}&size=${size}&sortBy=${sortBy}&direction=${direction}`;
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(criteria),
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return await response.json();
        } catch (error) {
            console.error("Error searching declarations:", error);
            throw error;
        }
    }
};
