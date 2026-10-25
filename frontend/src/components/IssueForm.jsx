import { useState } from 'react'
import './IssueForm.css'
import axios from 'axios'

function IssueForm({ onCreateIssue, editingIssue, onUpdateIssue }) {
    const [formData, setFormData] = useState({
        title: editingIssue?.title ?? '',
        description: editingIssue?.description ?? '',
        priority: editingIssue?.priority ?? 'MEDIUM',
        owner: editingIssue?.owner ?? '',
        startDate: editingIssue?.startDate ?? '',
        endDate: editingIssue?.endDate ?? ''
    })

    const [aiSuggestion, setAiSuggestion] = useState(null)
    const [isSuggesting, setIsSuggesting] = useState(false)

    function handleChange(e) {
        const { name, value } = e.target

        setFormData(prev => ({
            ...prev,
            [name]: value
        }))
    }

    const handleSuggestPriority = async () => {
        setIsSuggesting(true)
        setAiSuggestion(null)

        try {
            const response = await axios.post('/api/ai/suggest-priority', {
                title: formData.title,
                description: formData.description
            })

            setAiSuggestion(response.data)
        } catch (error) {
            console.error('AI prioritisation failed:', error)
        } finally {
            setIsSuggesting(false)
        }
    }

    async function handleSubmit(e) {
        e.preventDefault()
        
        try {
            if (editingIssue) {
                await onUpdateIssue(editingIssue.id, formData)
            } else {
                await onCreateIssue(formData)
            }

            setFormData({
                title: '',
                description: '',
                priority: 'MEDIUM',
                owner: '',
                startDate: '',
                endDate: ''
            })

            setAiSuggestion(null)
        } catch (error) {
            console.error('Unable to submit form:', error)
        }
    }
 
    return (
        <form className="issue-form" onSubmit={handleSubmit}>
            <input
                type="text"
                name="title"
                placeholder="Issue title"
                value={formData.title}
                onChange={handleChange}
                required
            />
            
            <textarea
                type="text"
                name="description"
                placeholder="Description"
                value={formData.description}
                onChange={handleChange}
            />

            <select 
                name="priority" 
                value={formData.priority}
                onChange={handleChange}
            >
                <option value="LOW">Low</option>
                <option value="MEDIUM">Medium</option>
                <option value="HIGH">High</option>
            </select>

            <button
                type="button"
                className="ai-suggest-button"
                onClick={handleSuggestPriority}
                disabled={isSuggesting || !formData.title.trim()}
            >
                {isSuggesting ? 'Analysing issue...' : 'Suggest priority with AI'}
            </button>

            {aiSuggestion && (
                <div className="ai-suggestion">
                    <h4>AI Recommendation</h4>

                    <p className="ai-suggestion-subheading">
                        Suggested priority:
                        <strong> {aiSuggestion.priority}</strong>
                    </p>

                    <p>{aiSuggestion.reason}</p>

                    <button
                        type="button"
                        onClick={() => {
                            setFormData(prev => ({
                            ...prev,
                            priority: aiSuggestion.priority
                            }))
                            setAiSuggestion(null)
                        }}
                    >
                        Apply suggestion
                    </button>
                </div>
            )}

            <input
                type="text"
                name="owner"
                placeholder="Issue owner"
                value={formData.owner}
                onChange={handleChange}
                required
            />

            <input 
                type="date" 
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                required
            />

            <input
                type="date" 
                name="endDate" 
                value={formData.endDate}
                onChange={handleChange}
                required
            />

            <button type="submit">
                {editingIssue ? 'Update issue' : 'Create issue'}
            </button>
        </form>
    )
}

export default IssueForm